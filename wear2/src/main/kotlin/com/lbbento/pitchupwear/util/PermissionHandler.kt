package com.lbbento.pitchupwear.util

import android.Manifest.permission.RECORD_AUDIO
import android.app.Activity
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.support.v4.app.ActivityCompat.requestPermissions
import android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.support.wearable.view.WearableDialogHelper.DialogBuilder
import com.lbbento.pitchupwear.R
import com.lbbento.pitchupwear.di.ForActivity
import javax.inject.Inject


@ForActivity
class PermissionHandler @Inject constructor(val activity: Activity) {

    private val AUDIO_PERMISSION_REQUEST_CODE = 4

    fun handleMicrophonePermission(): Boolean {
        if (hasMicrophonePermission())
            return true

        if (shouldShowRequestPermissionRationale(activity, RECORD_AUDIO)) {
            showMicPermissionDialog()
        } else {
            requestMicPermisson()
        }
        return false
    }

    private fun hasMicrophonePermission(): Boolean {
        val permissionCheck = checkSelfPermission(activity.applicationContext, RECORD_AUDIO)
        if (permissionCheck == PERMISSION_GRANTED) {
            return true
        }
        return false
    }

    private fun showMicPermissionDialog() {
        DialogBuilder(activity, R.style.Theme_Wearable_Modal)
                .setMessage(R.string.permisson_handler_mic_permission)
                .setCancelable(false)
                .setPositiveButton(R.string.permission_handler_mic_permission_button_ok, { _, _ -> requestMicPermisson() })
                .create()
                .show()
    }

    private fun requestMicPermisson() {
        requestPermissions(activity, arrayOf(RECORD_AUDIO), AUDIO_PERMISSION_REQUEST_CODE)
    }
}
