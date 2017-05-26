package com.lbbento.daydreamnasa.di

import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class AppSchedulers @Inject constructor() {

    open fun io(): Scheduler {
        return Schedulers.io()
    }

    open fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}