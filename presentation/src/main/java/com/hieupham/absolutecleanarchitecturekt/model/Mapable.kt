package com.hieupham.absolutecleanarchitecturekt.model

interface Mapable<T, R> {

    fun map(data: T): R
}