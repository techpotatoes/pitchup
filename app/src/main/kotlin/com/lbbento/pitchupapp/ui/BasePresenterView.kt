package com.lbbento.pitchupapp.ui

interface BasePresenterView {
    fun onAttachedToWindow(view: BaseView)
    fun onCreated()
    fun onStop()
    fun onViewResumed()
    fun onViewResuming()
}