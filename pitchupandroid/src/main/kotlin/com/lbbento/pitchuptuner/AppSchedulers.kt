package com.lbbento.pitchuptuner

import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

internal open class AppSchedulers {
    open fun io() = Schedulers.io()!!

    open fun computation() = Schedulers.computation()!!

    open fun ui() = AndroidSchedulers.mainThread()!!
}
