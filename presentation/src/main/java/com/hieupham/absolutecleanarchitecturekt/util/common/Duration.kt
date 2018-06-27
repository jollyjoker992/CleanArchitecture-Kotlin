package com.hieupham.absolutecleanarchitecturekt.util.common

import java.util.concurrent.TimeUnit

class Duration private constructor(val durationMillis: Long) {

    companion object {

        fun from(durationMillis: Long): Duration {
            return Duration(durationMillis)
        }

        fun from(duration: Long, unit: TimeUnit): Duration {
            return Duration(unit.toMillis(duration))
        }
    }
}