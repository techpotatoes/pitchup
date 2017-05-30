package com.lbbento.pitchupwear.main

import com.lbbento.pitchupwear.AppSchedulers
import com.lbbento.pitchupwear.di.ForActivity
import com.lbbento.pitchupwear.ui.BasePresenter
import com.lbbento.pitchupwear.util.PermissionHandler
import javax.inject.Inject

@ForActivity
class MainPresenter @Inject constructor(val appSchedulers: AppSchedulers, val permissionHandler: PermissionHandler, val tunerService: com.lbbento.pitchuptuner.service.TunerService) : BasePresenter<MainView>() {

    override fun onViewResuming() {
        if (permissionHandler.handleMicrophonePermission()) {
            tunerService.getNotes()
                    .subscribeOn(appSchedulers.io())
                    .observeOn(appSchedulers.ui())
                    .subscribe()
        }
    }
}