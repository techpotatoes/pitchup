package com.lbbento.pitchupapp.util

import android.Manifest.permission.RECORD_AUDIO
import android.app.Activity
import android.support.v4.app.ActivityCompat.requestPermissions
import android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.support.v4.content.PermissionChecker.PERMISSION_GRANTED
import com.lbbento.pitchupapp.di.ForActivity
import javax.inject.Inject


@ForActivity
class PermissionHelper @Inject constructor(val activity: Activity) {

    private val AUDIO_PERMISSION_REQUEST_CODE = 4

    fun handleMicrophonePermission(): Boolean {
        if (hasMicrophonePermission())
            return true

        if (shouldShowRequestPermissionRationale(activity, RECORD_AUDIO)) {
            showMicPermissionDialog()
        } else {
            requestPermissions(activity, arrayOf(RECORD_AUDIO), AUDIO_PERMISSION_REQUEST_CODE)
        }
        return false
    }

    private fun hasMicrophonePermission() =
            checkSelfPermission(activity.applicationContext, RECORD_AUDIO) == PERMISSION_GRANTED


    private fun showMicPermissionDialog() {
        //TODO
    }
}
