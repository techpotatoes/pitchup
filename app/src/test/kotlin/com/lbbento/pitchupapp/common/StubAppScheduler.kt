package com.lbbento.pitchupapp.common

import com.lbbento.pitchupapp.AppSchedulers
import rx.Scheduler
import rx.schedulers.Schedulers

class StubAppScheduler : AppSchedulers() {

    override fun io(): Scheduler {
        return Schedulers.immediate()
    }

    override fun ui(): Scheduler {
        return Schedulers.immediate()
    }

    override fun computation(): Scheduler {
        return Schedulers.immediate()
    }
}