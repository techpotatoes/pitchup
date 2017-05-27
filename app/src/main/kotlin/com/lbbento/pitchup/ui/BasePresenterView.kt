package com.lbbento.pitchup.ui

interface BasePresenterView {
    fun onAttachedToWindow(view: BaseView)
    fun onViewResumed()
    fun onViewResuming()
}