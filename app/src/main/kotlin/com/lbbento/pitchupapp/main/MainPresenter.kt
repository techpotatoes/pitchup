package com.lbbento.pitchupapp.main

import com.lbbento.pitchupapp.AppSchedulers
import com.lbbento.pitchupapp.di.ForActivity
import com.lbbento.pitchupapp.ui.BasePresenter
import com.lbbento.pitchupapp.util.PermissionHelper
import com.lbbento.pitchupcore.TuningStatus.DEFAULT
import com.lbbento.pitchuptuner.GuitarTunerReactive
import javax.inject.Inject

@ForActivity
class MainPresenter @Inject constructor(val appSchedulers: AppSchedulers,
                                        val permissionHelper: PermissionHelper,
                                        val guitarTunerReactive: GuitarTunerReactive,
                                        val mapper: TunerServiceMapper) : BasePresenter<MainView>() {

    override fun onCreated() {
        super.onCreated()
        mView.setupGauge()
    }

    override fun onViewResuming() {
        if (permissionHelper.handleMicrophonePermission()) {
            guitarTunerReactive.listenToNotes()
                    .subscribeOn(appSchedulers.computation())
                    .observeOn(appSchedulers.ui())
                    .subscribeAndManage(
                            { tunerResultReceived(mapper.tunerResultToViewModel(it!!)) },
                            { tunerResultError() })
        }
    }

    private fun tunerResultReceived(tunerViewModel: TunerViewModel) =
            when (tunerViewModel.tuningStatus) {
                DEFAULT -> mView.updateToDefaultStatus()
                else -> {
                    mView.updateNote(tunerViewModel.note)
                    mView.updateIndicator((tunerViewModel.diffInCents * -1).toFloat())
                    mView.updateCurrentFrequency(
                            (tunerViewModel.expectedFrequency + (tunerViewModel.diffFrequency * -1)).toFloat())
                    mView.updateCurrentDifferenceInCents(
                            tunerViewModel.diffInCents.toFloat())
                }
            }

    private fun tunerResultError() {
        mView.informError()
    }
}