package com.hieupham.data.source.local

import com.hieupham.data.source.local.api.DatabaseApi
import com.hieupham.data.source.local.api.SharedPrefApi
import io.reactivex.*
import io.reactivex.schedulers.Schedulers

/**
 * Created by hieupham on 6/26/18.
 */
abstract class LocalDataSource(private val databaseApi: DatabaseApi,
        private val sharedPrefApi: SharedPrefApi) {

    protected fun <T> maybeDb(function: (DatabaseApi) -> Maybe<T>): Maybe<T> {
        return databaseApi.maybe(function)
    }

    protected fun <T> flowableDb(function: (DatabaseApi) -> Flowable<T>): Flowable<T> {
        return databaseApi.flowable(function)
    }

    protected fun <T> singleDb(function: (DatabaseApi) -> Single<T>): Single<T> {
        return databaseApi.single(function)
    }

    protected fun <T> observableDb(function: (DatabaseApi) -> Observable<T>): Observable<T> {
        return databaseApi.observable(function)
    }

    protected fun completableDb(function: (DatabaseApi) -> Completable): Completable {
        return databaseApi.completable(function)
    }

    protected fun <T> maybeDb(action: (MaybeEmitter<in T>, DatabaseApi) -> Unit): Maybe<T> {
        return databaseApi.maybe(action)
    }

    protected fun <T> flowableDb(
            action: (FlowableEmitter<in T>, DatabaseApi) -> Unit): Flowable<T> {
        return databaseApi.flowable(action)
    }

    protected fun <T> singleDb(action: (SingleEmitter<in T>, DatabaseApi) -> Unit): Single<T> {
        return databaseApi.single(action)
    }

    protected fun <T> observableDb(
            action: (ObservableEmitter<in T>, DatabaseApi) -> Unit): Observable<T> {
        return databaseApi.observable(action)
    }

    protected fun completableSharedPref(action: (SharedPrefApi) -> Unit): Completable {
        return Completable.create { e ->
            action.invoke(sharedPrefApi)
            e.onComplete()
        }.subscribeOn(Schedulers.io())
    }

    protected fun <T> singleSharedPref(
            action: (SingleEmitter<in T>, SharedPrefApi) -> Unit): Single<T> {
        return Single.create(SingleOnSubscribe<T> { emitter ->
            action.invoke(emitter, sharedPrefApi)
        }).subscribeOn(Schedulers.io())
    }
}