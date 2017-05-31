package com.lbbento.pitchupwear.main

import android.util.Log
import com.lbbento.pitchuptuner.service.TunerResult
import com.lbbento.pitchuptuner.service.TunerService
import com.lbbento.pitchuptuner.service.TuningStatus
import com.lbbento.pitchupwear.AppSchedulers
import com.lbbento.pitchupwear.di.ForActivity
import com.lbbento.pitchupwear.ui.BasePresenter
import com.lbbento.pitchupwear.util.PermissionHandler
import javax.inject.Inject

@ForActivity
class MainPresenter @Inject constructor(val appSchedulers: AppSchedulers, val permissionHandler: PermissionHandler, val tunerService: TunerService, val mapper: TunerServiceMapper) : BasePresenter<MainView>() {

    override fun onCreate() {
        super.onCreate()
        mView.setAmbientEnabled()
    }

    override fun onViewResuming() {
        if (permissionHandler.handleMicrophonePermission()) {
            tunerService.getNotes()
                    .subscribeOn(appSchedulers.io())
                    .observeOn(appSchedulers.ui())
                    .subscribe(
                            { tunerResult: TunerResult? -> tunerResultReceived(mapper.tunerResultToViewModel(tunerResult!!)) },
                            { _: Throwable -> tunerResultError() })
        }
    }

    private fun tunerResultReceived(tunerViewModel: TunerViewModel) {
        mView.updateTunerView(tunerViewModel = tunerViewModel)

        //Testing
        when (tunerViewModel.tunningStatus) {
            TuningStatus.TUNED -> Log.e("Lucas", (String.format("You are tuned to %s", tunerViewModel.note)))
            TuningStatus.TOO_LOW -> Log.e("Lucas", (String.format("Almost tuned, a little up to %s", tunerViewModel.note)))
            TuningStatus.TOO_HIGH -> Log.e("Lucas", (String.format("Almost tuned, a little down to %s", tunerViewModel.note)))
            TuningStatus.WAY_TOO_LOW -> Log.e("Lucas", (String.format("Too flat! tune up a bit. Tuning %s", tunerViewModel.note)))
            TuningStatus.WAY_TOO_HIGH -> Log.e("Lucas", (String.format("Too sharp! tune down a bit. Tuning %s", tunerViewModel.note)))
            else -> {
                Log.e("Lucas", ("DEFAULT STATE"))
            }
        }
    }

    private fun tunerResultError() {
        Log.e("Lucas", "Error tuning") //TODO
    }
}