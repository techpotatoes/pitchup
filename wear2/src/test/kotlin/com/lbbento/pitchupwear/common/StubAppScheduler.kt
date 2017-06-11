package com.lbbento.pitchupwear.common

import com.lbbento.pitchupwear.AppSchedulers
import rx.schedulers.Schedulers

class StubAppScheduler : AppSchedulers() {
    override fun io() = Schedulers.immediate()!!

    override fun ui() = Schedulers.immediate()!!
}