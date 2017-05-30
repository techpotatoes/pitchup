package com.lbbento.pitchupwear.ui

interface BasePresenterView {
    fun onAttachedToWindow(view: BaseView)
    fun onViewResumed()
    fun onViewResuming()
}