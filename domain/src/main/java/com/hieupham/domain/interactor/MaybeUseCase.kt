package com.hieupham.domain.interactor

import io.reactivex.Maybe

/**
 * Created by hieupham on 6/26/18.
 */
abstract class MaybeUseCase<I : UseCase.Input, O> : UseCase<I, Maybe<O>>() {

    fun execute(input: I, output: Observer<O>) {
        subscribe(buildDataStream(input).doOnSubscribe(output.onSubscribe())
                .subscribe(output.onSuccess(), output.onError(), output.onComplete()))
    }
}