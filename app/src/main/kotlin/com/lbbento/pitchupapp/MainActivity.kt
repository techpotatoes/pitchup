package com.lbbento.pitchupapp

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
import com.lbbento.pitchuptuner.GuitarTuner
import com.lbbento.pitchuptuner.audio.PitchAudioRecorder
import com.lbbento.pitchuptuner.service.TunerResult
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

    private val guitarTuner = GuitarTuner(audioRecordWrapper)

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //        requestPermissions(new String[]{RECORD_AUDIO}, 4);

        guitarTuner.listenToNotes().subscribeOn(Schedulers.io())
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

    private fun updateTunerView(tunerViewModel: TunerResult) {
        println(format("Exp: %f  Diff: %f  Note: %s", tunerViewModel.expectedFrequency, tunerViewModel.diffFrequency, tunerViewModel.note))
        val text = findViewById(R.id.text) as TextView
        text.text = tunerViewModel.note
    }
}
