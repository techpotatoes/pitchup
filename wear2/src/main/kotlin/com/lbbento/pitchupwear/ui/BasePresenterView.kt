package com.lbbento.pitchupwear.ui

interface BasePresenterView {
    fun onAttachedToWindow(view: BaseView)
    fun onCreated()
    fun onViewResumed()
    fun onViewResuming()
}