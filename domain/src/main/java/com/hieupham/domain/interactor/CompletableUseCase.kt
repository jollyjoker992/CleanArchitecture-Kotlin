package com.hieupham.domain.interactor

import io.reactivex.Completable

/**
 * Created by hieupham on 6/26/18.
 */
abstract class CompletableUseCase<I : UseCase.Input> : UseCase<I, Completable>() {

    fun execute(input: I, output: Observer<Any>) {
        subscribe(buildDataStream(input).doOnSubscribe(output.onSubscribe())
                .subscribe(output.onComplete(), output.onError()))
    }
}