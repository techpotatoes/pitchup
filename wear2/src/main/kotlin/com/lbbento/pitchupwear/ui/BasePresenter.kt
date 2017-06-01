package com.lbbento.pitchupwear.ui

import android.support.annotation.CallSuper

abstract class BasePresenter<V : BaseView> : BasePresenterView {

    lateinit var mView: V

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
    }
}