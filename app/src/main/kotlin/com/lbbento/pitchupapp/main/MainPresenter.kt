package com.lbbento.pitchupapp.main

import com.lbbento.pitchupapp.AppSchedulers
import com.lbbento.pitchupapp.di.ForActivity
import com.lbbento.pitchupapp.ui.BasePresenter
import com.lbbento.pitchupapp.util.PermissionHelper
import com.lbbento.pitchupcore.TuningStatus.DEFAULT
import com.lbbento.pitchuptuner.GuitarTunerReactive
import rx.Subscription
import javax.inject.Inject

@ForActivity
class MainPresenter @Inject constructor(val appSchedulers: AppSchedulers, val permissionHelper: PermissionHelper, val guitarTunerReactive: GuitarTunerReactive, val mapper: TunerServiceMapper) : BasePresenter<MainView>() {

    var tunerServiceSubscription: Subscription? = null

    override fun onCreated() {
        super.onCreated()
        mView.setupGauge()
    }

    override fun onStop() {
        tunerServiceSubscription!!.unsubscribe()
    }

    override fun onViewResuming() {
        if (permissionHelper.handleMicrophonePermission()) {
            tunerServiceSubscription = guitarTunerReactive.listenToNotes()
                    .subscribeOn(appSchedulers.io())
                    .observeOn(appSchedulers.ui())
                    .subscribe(
                            { tunerResultReceived(mapper.tunerResultToViewModel(it!!)) },
                            { tunerResultError() })
        }
    }

    private fun tunerResultReceived(tunerViewModel: TunerViewModel) {
        if (tunerViewModel.tuningStatus != DEFAULT) {
            mView.updateNote(tunerViewModel.note)
            mView.updateIndicator((tunerViewModel.diffInCents * -1).toFloat())
            mView.updateCurrentFrequency((tunerViewModel.expectedFrequency + (tunerViewModel.diffFrequency * -1)).toFloat())
        } else {
            mView.updateToDefaultStatus()
        }
    }

    private fun tunerResultError() {
        mView.informError()
    }
}