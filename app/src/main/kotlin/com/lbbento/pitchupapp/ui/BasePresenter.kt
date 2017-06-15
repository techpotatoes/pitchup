package com.lbbento.pitchupapp.ui

import android.support.annotation.CallSuper
import rx.Observable
import rx.subscriptions.CompositeSubscription

abstract class BasePresenter<V : BaseView> : Presenter {

    lateinit var mView: V

    open val subscriptions: CompositeSubscription by lazy { CompositeSubscription() }

    @CallSuper
    override fun onAttachedToWindow(view: BaseView) {
        @Suppress("UNCHECKED_CAST")
        this.mView = view as V
    }

    @CallSuper
    override fun onViewResumed() {
    }

    @CallSuper
    override fun onViewResuming() {
    }

    @CallSuper
    override fun onCreated() {
    }

    @CallSuper
    override fun onStop() {
        subscriptions.unsubscribe()
    }

    fun <T> Observable<T>.subscribeAndManage(onNext: (T) -> Unit = {},
                                             onError: () -> Unit = {},
                                             onComplete: () -> Unit = {}) {
        subscriptions.add(subscribe(
                { result -> onNext(result) },
                { onError() },
                { onComplete() }))
    }
}