package com.lbbento.pitchup.common

import com.lbbento.pitchup.AppSchedulers
import rx.Scheduler
import rx.schedulers.Schedulers

class StubAppScheduler : AppSchedulers() {

    override fun io(): Scheduler {
        return Schedulers.immediate()
    }

    override fun ui(): Scheduler {
        return Schedulers.immediate()
    }
}