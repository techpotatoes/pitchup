package com.lbbento.pitchupwear.main

import com.lbbento.pitchupcore.TuningStatus.DEFAULT
import com.lbbento.pitchuptuner.GuitarTunerReactive
import com.lbbento.pitchupwear.AppSchedulers
import com.lbbento.pitchupwear.di.ForActivity
import com.lbbento.pitchupwear.ui.BasePresenter
import com.lbbento.pitchupwear.util.PermissionHelper
import rx.Subscription
import javax.inject.Inject

@ForActivity
class MainPresenter @Inject constructor(val appSchedulers: AppSchedulers,
                                        val permissionHelper: PermissionHelper,
                                        val guitarTunerReactive: GuitarTunerReactive,
                                        val mapper: TunerServiceMapper) : BasePresenter<MainView>() {

    var tunerServiceSubscription: Subscription? = null

    override fun onCreated() {
        super.onCreated()
        mView.setAmbientEnabled()
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