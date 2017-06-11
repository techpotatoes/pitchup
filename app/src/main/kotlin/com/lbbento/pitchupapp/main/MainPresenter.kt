package com.lbbento.pitchupapp.main

import android.util.Log
import com.lbbento.pitchupapp.AppSchedulers
import com.lbbento.pitchupapp.di.ForActivity
import com.lbbento.pitchupapp.ui.BasePresenter
import com.lbbento.pitchupapp.util.PermissionHandler
import com.lbbento.pitchupcore.TuningStatus.DEFAULT
import com.lbbento.pitchuptuner.GuitarTunerReactive
import rx.Subscription
import javax.inject.Inject

@ForActivity
class MainPresenter @Inject constructor(val appSchedulers: AppSchedulers, val permissionHandler: PermissionHandler, val guitarTunerReactive: GuitarTunerReactive, val mapper: TunerServiceMapper) : BasePresenter<MainView>() {

    private var tunerServiceSubscription: Subscription? = null

    override fun onCreated() {
        super.onCreated()
        mView.setupGauge()
    }

    override fun onStop() {
        tunerServiceSubscription!!.unsubscribe()
    }

    override fun onViewResuming() {
        if (permissionHandler.handleMicrophonePermission()) {
            tunerServiceSubscription = guitarTunerReactive.listenToNotes()
                    .subscribeOn(appSchedulers.io())
                    .observeOn(appSchedulers.ui())
                    .subscribe(
                            { tunerResultReceived(mapper.tunerResultToViewModel(it!!)) },
                            this::tunerResultError)
        }
    }

    private fun tunerResultReceived(tunerViewModel: TunerViewModel) {
        if (tunerViewModel.tunningStatus != DEFAULT) {
            mView.updateNote(tunerViewModel.note)
            mView.updateIndicator((tunerViewModel.diffInCents * -1).toFloat())
            mView.updateCurrentFrequency((tunerViewModel.expectedFrequency + (tunerViewModel.diffFrequency * -1)).toFloat())
        } else {
            mView.updateToDefaultStatus()
        }
    }

    private fun tunerResultError(e: Throwable) {
        mView.informError()
        Log.e(javaClass.name, "Error tuning: " + e.stackTrace.toString())
    }
}