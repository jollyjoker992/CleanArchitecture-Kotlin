package com.hieupham.domain.interactor

import io.reactivex.Single

/**
 * Created by hieupham on 6/26/18.
 */
abstract class SingleUseCase<I : UseCase.Input, O> : UseCase<I, Single<O>>() {

    fun execute(input: I, output: Observer<O>) {
        subscribe(buildDataStream(input).doOnSubscribe(output.onSubscribe())
                .subscribe(output.onSuccess(), output.onError()))
    }
}