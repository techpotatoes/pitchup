package com.lbbento.pitchupapp.util

import android.Manifest.permission.RECORD_AUDIO
import android.app.Activity
import android.support.v4.app.ActivityCompat.requestPermissions
import android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.support.v4.content.PermissionChecker.PERMISSION_GRANTED
import android.support.v7.app.AlertDialog
import com.lbbento.pitchupapp.R
import com.lbbento.pitchupapp.di.ForActivity
import javax.inject.Inject


@ForActivity
class PermissionHelper @Inject constructor(val activity: Activity) {

    private val AUDIO_PERMISSION_REQUEST_CODE = 4
    private val hasMicrophonePermission
        get() = checkSelfPermission(activity.applicationContext, RECORD_AUDIO) == PERMISSION_GRANTED


    fun handleMicrophonePermission(): Boolean {
        if (hasMicrophonePermission)
            return true

        if (shouldShowRequestPermissionRationale(activity, RECORD_AUDIO)) {
            showMicPermissionDialog()
        } else {
            requestMicPermission()
        }
        return false
    }

    private fun showMicPermissionDialog() {
        AlertDialog.Builder(activity)
                .setMessage(R.string.permisson_handler_mic_permission)
                .setCancelable(false)
                .setPositiveButton(R.string.permission_handler_mic_permission_button_ok, { _, _ -> requestMicPermission() })
                .create()
                .show()
    }

    private fun requestMicPermission() {
        requestPermissions(activity, arrayOf(RECORD_AUDIO), AUDIO_PERMISSION_REQUEST_CODE)
    }

}
