package com.lbbento.pitchupwear.ui

interface BasePresenterView {
    fun onAttachedToWindow(view: BaseView)
    fun onCreated()
    fun onStop()
    fun onViewResumed()
    fun onViewResuming()
}