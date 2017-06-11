package com.lbbento.pitchuptuner.common

import com.lbbento.pitchuptuner.AppSchedulers
import rx.Scheduler
import rx.schedulers.Schedulers

internal class StubAppScheduler : AppSchedulers() {

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