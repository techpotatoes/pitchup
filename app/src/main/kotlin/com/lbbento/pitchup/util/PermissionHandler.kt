package com.lbbento.pitchup.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.lbbento.pitchup.di.ForController
import javax.inject.Inject

@ForController
class PermissionHandler @Inject constructor(val activity: Activity) {

    private val AUDIO_PERMISSION_REQUEST_CODE = 4

    fun handleMicrophonePermission() {
        if (!hasMicrophonePermission()) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.RECORD_AUDIO), AUDIO_PERMISSION_REQUEST_CODE)
        }
    }

    private fun hasMicrophonePermission(): Boolean {
        val permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }
}
