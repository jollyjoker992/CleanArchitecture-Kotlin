package com.hieupham.absolutecleanarchitecturekt.util.common

import android.os.Handler
import android.os.Looper
import java.util.*
import javax.inject.Inject

class IntervalScheduler @Inject constructor() {

    interface SchedulerListener {
        fun onSchedule()
    }

    private var delay = 0L
    private var period = 0L
    private var timer: Timer? = null
    private val schedulerListeners: MutableList<SchedulerListener> = mutableListOf()
    private var isCancelled = false
    private var triggerOnMain = false

    fun triggerOnMain(triggerOnMain: Boolean): IntervalScheduler {
        this.triggerOnMain = triggerOnMain
        return this
    }

    fun delay(duration: Duration): IntervalScheduler {
        this.delay = duration.durationMillis
        return this
    }

    fun period(duration: Duration): IntervalScheduler {
        this.period = duration.durationMillis
        return this
    }

    fun period(): Duration {
        return Duration.from(period)
    }

    fun schedule() {
        cancel()
        timer = Timer()
        isCancelled = false
        if (period > 0) {
            timer?.schedule(task(), delay, period)
        } else {
            timer?.schedule(task(), delay)
        }
    }

    fun cancel() {
        if (timer == null || isCancelled) return
        timer?.cancel()
        timer = null
        isCancelled = true
    }

    fun observe(listener: SchedulerListener) {
        if (!schedulerListeners.contains(listener)) {
            schedulerListeners.add(listener)
        }
    }

    fun unObserve(listener: SchedulerListener) {
        schedulerListeners.remove(listener)
        if (schedulerListeners.isEmpty() && timer != null) {
            cancel()
        }

    }

    private fun onSchedule() {
        for (listener in schedulerListeners) {
            listener.onSchedule()
        }
    }

    private fun task(): TimerTask {
        return object : TimerTask() {
            override fun run() {
                if (triggerOnMain) {
                    Handler(Looper.getMainLooper()).post { onSchedule() }
                } else {
                    onSchedule()
                }
            }
        }
    }
}