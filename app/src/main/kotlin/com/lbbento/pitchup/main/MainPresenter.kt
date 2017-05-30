package com.lbbento.pitchup.main

import com.lbbento.pitchup.AppSchedulers
import com.lbbento.pitchup.di.ForActivity
import com.lbbento.pitchup.service.TunerService
import com.lbbento.pitchup.ui.BasePresenter
import com.lbbento.pitchup.util.PermissionHandler
import javax.inject.Inject

@ForActivity
class MainPresenter @Inject constructor(val appSchedulers: AppSchedulers, val permissionHandler: PermissionHandler, val tunerService: TunerService) : BasePresenter<MainView>() {

    override fun onViewResuming() {
        if (permissionHandler.handleMicrophonePermission()) {
            tunerService.getNotes()
                    .subscribeOn(appSchedulers.io())
                    .observeOn(appSchedulers.ui())
                    .subscribe()
        }
    }
}