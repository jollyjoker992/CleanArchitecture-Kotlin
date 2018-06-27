package com.hieupham.domain.interactor

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by hieupham on 6/26/18.
 */
abstract class UseCase<I : UseCase.Input, O> {

    private val compositeDisposable = CompositeDisposable()

    protected abstract fun buildDataStream(input: I): O

    internal fun subscribe(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun dispose() {
        compositeDisposable.clear()
    }

    abstract class Input

    open class EmptyInput private constructor() : Input() {

        companion object {

            fun instance(): EmptyInput {
                return EmptyInput()
            }
        }
    }
}