package com.lbbento.daydreamnasa.ui.presenter

import com.lbbento.pitchup.ui.BaseView


abstract class BasePresenter<V : BaseView> {

    lateinit var mView: V

    fun onAttachedToWindow(view: V) {
        this.mView = view
    }
}