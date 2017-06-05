package com.lbbento.pitchuptuner

import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

internal open class AppSchedulers {

    open fun io(): Scheduler {
        return Schedulers.io()
    }

    open fun computation(): Scheduler {
        return Schedulers.computation()
    }

    open fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}