package com.lbbento.pitchupapp

import android.annotation.SuppressLint
import android.media.AudioFormat.CHANNEL_IN_DEFAULT
import android.media.AudioFormat.ENCODING_PCM_16BIT
import android.media.AudioRecord
import android.media.AudioRecord.getMinBufferSize
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.lbbento.pitchupapp.util.AudioRecorderUtil.Companion.getSampleRate
import com.lbbento.pitchuptuner.GuitarTunerReactive
import com.lbbento.pitchuptuner.audio.PitchAudioRecorder
import com.lbbento.pitchuptuner.service.TunerResult
import com.lbbento.pitchuptuner.service.TuningStatus
import com.lbbento.pitchuptunergauge.view.TunerGauge
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.lang.String.format

class MainActivity : AppCompatActivity() {

    //TESTING
    private val audioRecord = AudioRecord(MediaRecorder.AudioSource.DEFAULT,
            getSampleRate(),
            CHANNEL_IN_DEFAULT,
            ENCODING_PCM_16BIT,
            getMinBufferSize(getSampleRate(), CHANNEL_IN_DEFAULT, ENCODING_PCM_16BIT))

    private val audioRecordWrapper = PitchAudioRecorder(audioRecord)

    private val guitarTuner = GuitarTunerReactive(audioRecordWrapper)

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupSpeedometer()

        //        requestPermissions(new String[]{RECORD_AUDIO}, 4);

        guitarTuner.listenToNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<TunerResult> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        Log.i("Lucas", "Error")
                        e.printStackTrace()
                    }

                    override fun onNext(tunerResult: TunerResult) {
                        updateTunerView(tunerResult)
                    }
                })


    }

    @SuppressLint("NewApi")
    private fun updateTunerView(tunerViewModel: TunerResult) {
        val text = findViewById(R.id.text) as TextView
        val speedometer = findViewById(R.id.gauge) as TunerGauge

        if (tunerViewModel.tunningStatus != TuningStatus.DEFAULT) {
            println(format("Exp: %f  Diff: %f  Note: %s", tunerViewModel.expectedFrequency, tunerViewModel.diffFrequency, tunerViewModel.note))
            if (text.text != tunerViewModel.note) {
                text.text = tunerViewModel.note

                speedometer.maxSpeed = (tunerViewModel.expectedFrequency + 10f).toInt() //TODO return it
                speedometer.minSpeed = (tunerViewModel.expectedFrequency - 10f).toInt()
                speedometer.speedPercentTo(50)
            }
            if ((tunerViewModel.expectedFrequency + tunerViewModel.diffFrequency) > speedometer.minSpeed ||
                    (tunerViewModel.expectedFrequency + tunerViewModel.diffFrequency) < speedometer.maxSpeed) {
                println(format("SppedTo: %f  Note: %s", (tunerViewModel.expectedFrequency + (tunerViewModel.diffFrequency * -1)).toFloat(), tunerViewModel.note))
                speedometer.indicatorColor = getColor(android.R.color.holo_orange_dark)
                speedometer.speedTo((tunerViewModel.expectedFrequency + (tunerViewModel.diffFrequency * -1)).toFloat(), 350)
            }
        } else if (text.text != "Play!")
            text.text = "Play!"
    }

    fun setupSpeedometer() {
        val speedometer: TunerGauge = findViewById(R.id.gauge) as TunerGauge

        // configure value range and ticks
        speedometer.maxSpeed = 880
        speedometer.minSpeed = 0
        speedometer.setSpeedAt(440f)

        speedometer.isWithTremble = false
    }

}
