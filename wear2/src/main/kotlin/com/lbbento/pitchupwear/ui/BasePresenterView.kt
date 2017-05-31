package com.lbbento.pitchupwear.ui

interface BasePresenterView {
    fun onAttachedToWindow(view: BaseView)
    fun onCreate()
    fun onViewResumed()
    fun onViewResuming()
}