package com.lbbento.pitchup.di

import android.app.Activity
import android.content.Context
import android.media.AudioFormat.CHANNEL_IN_DEFAULT
import android.media.AudioFormat.ENCODING_PCM_16BIT
import android.media.AudioRecord
import android.media.AudioRecord.getMinBufferSize
import android.media.MediaRecorder.AudioSource.DEFAULT
import be.tarsos.dsp.pitch.Yin
import com.lbbento.pitchup.audio.AudioRecordHelper.Companion.getReadSize
import com.lbbento.pitchup.audio.AudioRecordHelper.Companion.getSampleRate
import com.lbbento.pitchup.audio.AudioRecordWrapper
import com.lbbento.pitchup.service.TunerService
import com.lbbento.pitchup.service.TunerServiceImpl
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(val activity: Activity) {

    @Provides
    fun context(): Context {
        return activity
    }

    @Provides
    fun activityBase(): Activity {
        return activity
    }

    @Provides
    fun provideAudioRecord(): AudioRecord = AudioRecord(DEFAULT,
            getSampleRate(),
            CHANNEL_IN_DEFAULT,
            ENCODING_PCM_16BIT,
            getMinBufferSize(getSampleRate(), CHANNEL_IN_DEFAULT, ENCODING_PCM_16BIT))

    @Provides
    fun provideTunerService(audioRecord: AudioRecordWrapper, torsoYin: Yin): TunerService = TunerServiceImpl(audioRecord, torsoYin)

    @Provides
    fun provideTorsoYin(): Yin {
        return Yin(getSampleRate().toFloat(), getReadSize())
    }
}