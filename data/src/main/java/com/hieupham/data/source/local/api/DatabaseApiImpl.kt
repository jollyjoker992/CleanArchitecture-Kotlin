package com.hieupham.data.source.local.api

import com.hieupham.data.source.local.api.dao.AssetDao
import com.hieupham.data.source.local.api.dao.BlockDao
import com.hieupham.data.source.local.api.dao.TransactionDao
import io.reactivex.*
import io.reactivex.schedulers.Schedulers

/**
 * Created by hieupham on 6/26/18.
 */
open class DatabaseApiImpl(private val databaseManager: DatabaseManager) : DatabaseApi {

    override fun assetDao(): AssetDao {
        return databaseManager.assetDao()
    }

    override fun blockDao(): BlockDao {
        return databaseManager.blockDao()
    }

    override fun transactionDao(): TransactionDao {
        return databaseManager.transactionDao()
    }

    override fun <T> maybe(function: (DatabaseApi) -> Maybe<T>): Maybe<T> {
        return try {
            function.invoke(this).subscribeOn(Schedulers.io())
        } catch (e: Exception) {
            Maybe.error(e)
        }
    }

    override fun <T> flowable(function: (DatabaseApi) -> Flowable<T>): Flowable<T> {
        return try {
            function.invoke(this).subscribeOn(Schedulers.io())
        } catch (e: Exception) {
            Flowable.error(e)
        }
    }

    override fun <T> single(function: (DatabaseApi) -> Single<T>): Single<T> {
        return try {
            function.invoke(this).subscribeOn(Schedulers.io())
        } catch (e: Exception) {
            Single.error(e)
        }
    }

    override fun <T> observable(function: (DatabaseApi) -> Observable<T>): Observable<T> {
        return try {
            function.invoke(this).subscribeOn(Schedulers.io())
        } catch (e: Exception) {
            Observable.error(e)
        }
    }

    override fun completable(function: (DatabaseApi) -> Completable): Completable {
        return try {
            function.invoke(this).subscribeOn(Schedulers.io())
        } catch (e: Exception) {
            Completable.error(e)
        }
    }

    override fun <T> maybe(action: (MaybeEmitter<in T>, DatabaseApi) -> Unit): Maybe<T> {
        return Maybe.create { emitter ->
            action.invoke(emitter, this)
            emitter.onComplete()
        }
    }

    override fun <T> flowable(action: (FlowableEmitter<in T>, DatabaseApi) -> Unit): Flowable<T> {
        return Flowable.create({ emitter ->
            action.invoke(emitter, this)
            emitter.onComplete()
        }, BackpressureStrategy.BUFFER)
    }

    override fun <T> single(action: (SingleEmitter<in T>, DatabaseApi) -> Unit): Single<T> {
        return Single.create { emitter ->
            action.invoke(emitter, this)
        }
    }

    override fun <T> observable(
            action: (ObservableEmitter<in T>, DatabaseApi) -> Unit): Observable<T> {
        return Observable.create { emitter ->
            action.invoke(emitter, this)
            emitter.onComplete()
        }
    }


}