package com.hieupham.data.source.local.api

import com.hieupham.data.source.local.api.dao.AssetDao
import com.hieupham.data.source.local.api.dao.BlockDao
import com.hieupham.data.source.local.api.dao.TransactionDao
import io.reactivex.*

/**
 * Created by hieupham on 6/26/18.
 */
interface DatabaseApi {

    fun transactionDao(): TransactionDao

    fun assetDao(): AssetDao

    fun blockDao(): BlockDao

    fun <T> maybe(function: (DatabaseApi) -> Maybe<T>): Maybe<T>

    fun <T> flowable(function: (DatabaseApi) -> Flowable<T>): Flowable<T>

    fun <T> single(function: (DatabaseApi) -> Single<T>): Single<T>

    fun <T> observable(function: (DatabaseApi) -> Observable<T>): Observable<T>

    fun completable(function: (DatabaseApi) -> Completable): Completable

    fun <T> maybe(action: (MaybeEmitter<in T>, DatabaseApi) -> Unit): Maybe<T>

    fun <T> flowable(action: (FlowableEmitter<in T>, DatabaseApi) -> Unit): Flowable<T>

    fun <T> single(action: (SingleEmitter<in T>, DatabaseApi) -> Unit): Single<T>

    fun <T> observable(
            action: (ObservableEmitter<in T>, DatabaseApi) -> Unit): Observable<T>
}