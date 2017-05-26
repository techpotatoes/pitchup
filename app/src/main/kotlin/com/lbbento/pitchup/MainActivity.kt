package com.lbbento.pitchup

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.widget.TextView

class MainActivity : WearableActivity() {

    private var mTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTextView = findViewById(R.id.text) as TextView

        // Enables Always-on
        setAmbientEnabled()
    }
}
