package com.lbbento.pitchupapp.ui

interface Presenter {
    fun onAttachedToWindow(view: BaseView)
    fun onCreated()
    fun onStop()
    fun onViewResumed()
    fun onViewResuming()
}