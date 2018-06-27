package com.hieupham.data.utils.action

/**
 * Created by hieupham on 6/26/18.
 */
interface Action2<T1, T2> : Action {

    fun call(t1: T1, t2: T2)
}