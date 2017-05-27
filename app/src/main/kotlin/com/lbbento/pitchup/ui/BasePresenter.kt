package com.lbbento.pitchup.ui

abstract class BasePresenter<V : BaseView> : BasePresenterView {

    lateinit var mView: V

    override fun onAttachedToWindow(view: BaseView) {
        @Suppress("UNCHECKED_CAST")
        this.mView = view as V
    }
}