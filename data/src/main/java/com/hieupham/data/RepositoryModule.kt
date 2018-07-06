package com.hieupham.data

import android.arch.persistence.room.Room
import android.content.Context
import com.hieupham.data.source.TransactionRepositoryImpl
import com.hieupham.data.source.local.TransactionLocalDataSource
import com.hieupham.data.source.local.api.DatabaseApi
import com.hieupham.data.source.local.api.DatabaseApiImpl
import com.hieupham.data.source.local.api.DatabaseManager
import com.hieupham.data.source.local.api.MigrationManager
import com.hieupham.data.source.remote.Mapper
import com.hieupham.data.source.remote.api.TransactionRemoteDataSource
import com.hieupham.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by hieupham on 6/27/18.
 */

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideTransactionRepo(remoteDataSource: TransactionRemoteDataSource,
            localDataSource: TransactionLocalDataSource, mapper: Mapper): TransactionRepository {
        return TransactionRepositoryImpl(mapper, localDataSource,
                remoteDataSource)
    }

    @Singleton
    @Provides
    fun provideDatabaseApi(databaseManager: DatabaseManager): DatabaseApi {
        return DatabaseApiImpl(databaseManager)
    }

    @Singleton
    @Provides
    fun provideDatabaseManager(context: Context): DatabaseManager {
        return Room.databaseBuilder(context, DatabaseManager::class.java,
                DatabaseManager.DATABASE_NAME)
                .addMigrations(MigrationManager.MIGRATION_1_2)
                .build()
    }
}