package com.lbbento.pitchupapp.di

import android.app.Activity
import android.content.Context
import android.media.AudioFormat.CHANNEL_IN_DEFAULT
import android.media.AudioFormat.ENCODING_PCM_16BIT
import android.media.AudioRecord
import android.media.AudioRecord.getMinBufferSize
import android.media.MediaRecorder.AudioSource.DEFAULT
import com.lbbento.pitchupapp.util.AudioRecorderUtil.Companion.getSampleRate
import com.lbbento.pitchuptuner.GuitarTunerReactive
import com.lbbento.pitchuptuner.audio.PitchAudioRecorder
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ActivityModule(val activity: Activity) {

    @Provides
    fun context(): Context = activity.baseContext

    @Provides
    fun activityBase(): Activity = activity

    @Provides() @Named("sampleRate")
    fun provideSampleRate(): Int = getSampleRate()

    @Provides
    fun provideAudioRecord(@Named("sampleRate") sampleRate: Int): AudioRecord = AudioRecord(DEFAULT,
            sampleRate,
            CHANNEL_IN_DEFAULT,
            ENCODING_PCM_16BIT,
            getMinBufferSize(sampleRate, CHANNEL_IN_DEFAULT, ENCODING_PCM_16BIT))

    @Provides
    fun providerPitchAudioRecorder(audioRecord: AudioRecord): PitchAudioRecorder = PitchAudioRecorder(audioRecord)

    @Provides
    fun provideGuitarTuner(pitchAudioRecord: PitchAudioRecorder): GuitarTunerReactive = GuitarTunerReactive(pitchAudioRecord)
}