package com.lbbento.pitchupapp.ui

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import com.lbbento.pitchupapp.MainApplication
import com.lbbento.pitchupapp.di.ActivityComponent
import com.lbbento.pitchupapp.di.ActivityModule
import javax.inject.Inject

abstract class BaseActivity<P : BasePresenterView> : AppCompatActivity(), BaseView {

    @Inject
    lateinit var presenter: P

    val activityComponent: ActivityComponent
        get() = (application as MainApplication).component.plus(ActivityModule(this))

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        presenter.onAttachedToWindow(this)
        presenter.onCreated()
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewResuming()
    }

    override fun onPostResume() {
        super.onResume()
        presenter.onViewResumed()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    abstract fun setupInjection()
}