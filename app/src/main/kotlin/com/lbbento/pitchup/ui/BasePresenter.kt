package com.lbbento.pitchup.ui

abstract class BasePresenter<V : BaseView> {

    lateinit var mView: V

    fun onAttachedToWindow(view: V) {
        this.mView = view
    }
}