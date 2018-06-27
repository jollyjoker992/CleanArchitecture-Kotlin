package com.hieupham.data.utils.action

/**
 * Created by hieupham on 6/26/18.
 */
interface Action1<T> : Action {

    fun call(t: T)

}