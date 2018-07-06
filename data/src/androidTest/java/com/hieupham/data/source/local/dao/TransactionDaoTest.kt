package com.hieupham.data.source.local.dao

import android.database.sqlite.SQLiteConstraintException
import com.hieupham.data.data.AndroidTestDataProvider.Companion.assetDatas1
import com.hieupham.data.data.AndroidTestDataProvider.Companion.blockDatas1
import com.hieupham.data.data.AndroidTestDataProvider.Companion.sqliteConstraintException
import com.hieupham.data.data.AndroidTestDataProvider.Companion.transactionData1
import com.hieupham.data.data.AndroidTestDataProvider.Companion.transactionDatas1
import com.hieupham.data.model.TransactionData
import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers.containsString
import org.junit.Test

class TransactionDaoTest : DaoTest() {

    val dao = database.transactionDao()

    @Test
    fun testSaveTransactions_NoError_DataIsSaved() {
        val expectedResult = transactionDatas1()
        val observer = TestObserver<List<TransactionData>>()
        val transaction = transactionData1()
        val id = transaction.id
        val offset = transaction.offset
        database.blockDao().save(blockDatas1())
        database.assetDao().save(assetDatas1())

        dao.save(expectedResult)
        dao.get().subscribe(observer)

        observer.assertValue(expectedResult)
        observer.assertValue { transactions ->
            transactions.filter { transaction ->
                transaction.id == id
            }[0].offset == offset && transactions.size == expectedResult.size
        }

    }

    @Test
    fun testSaveTransactions_MissingForeignKeys_ErrorIsThrow() {
        val transactions = transactionDatas1()
        val expectedException = sqliteConstraintException()

        exceptionRule.expect(SQLiteConstraintException::class.java)
        exceptionRule.expectMessage(containsString(expectedException.message))
        dao.save(transactions)
    }

    @Test
    fun testSaveTransactions_DuplicateRecords_ReplacedNewOne() {
        val expectedResult = transactionDatas1()
        val observer = TestObserver<List<TransactionData>>()
        val transaction = transactionData1()
        val id = transaction.id
        val offset = transaction.offset
        database.blockDao().save(blockDatas1())
        database.assetDao().save(assetDatas1())
        dao.save(expectedResult)

        dao.save(expectedResult)
        dao.get().subscribe(observer)

        observer.assertValue(expectedResult)
        observer.assertValue { transactions ->
            transactions.filter { transaction ->
                transaction.id == id
            }[0].offset == offset && transactions.size == expectedResult.size
        }
    }

    @Test
    fun testGetTransactions_NoError_DataIsReturn() {
        val expectedResult = transactionDatas1()
        val observer = TestObserver<List<TransactionData>>()
        val transaction = transactionData1()
        val id = transaction.id
        val offset = transaction.offset
        database.blockDao().save(blockDatas1())
        database.assetDao().save(assetDatas1())
        dao.save(expectedResult)

        dao.get().subscribe(observer)

        observer.assertValue(expectedResult)
        observer.assertValue { transactions ->
            transactions.filter { transaction ->
                transaction.id == id
            }[0].offset == offset && transactions.size == expectedResult.size
        }
    }

    @Test
    fun testDeleteTransaction_NoError_DataIsDeleted() {
        val transaction = transactionData1()
        val observer = TestObserver<List<TransactionData>>()
        val id = transaction.id
        val offset = transaction.offset
        database.blockDao().save(blockDatas1())
        database.assetDao().save(assetDatas1())
        database.transactionDao().save(transactionDatas1())
        val expectedResult = transactionDatas1().toMutableList()
        expectedResult.remove(transaction)


        dao.delete(transaction)
        dao.get().subscribe(observer)

        observer.assertValue { trans ->
            trans.size == transactionDatas1().size - 1
        }
        observer.assertValue { trans ->
            trans.none { transaction ->
                transaction.id == id && transaction.offset == offset
            }
        }
        observer.assertValue { trans -> trans.containsAll(expectedResult) }
    }
}