package com.lbbento.pitchup.main

import com.lbbento.pitchup.di.ForController
import com.lbbento.pitchup.ui.BasePresenter
import com.lbbento.pitchup.util.PermissionHandler
import javax.inject.Inject

@ForController
class MainPresenter @Inject constructor(var permissionHandler: PermissionHandler) : BasePresenter<MainView>() {

    fun onCreate() {
        permissionHandler.handleMicrophonePermission()
    }

}