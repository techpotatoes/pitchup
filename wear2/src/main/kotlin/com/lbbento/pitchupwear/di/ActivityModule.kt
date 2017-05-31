package com.lbbento.pitchupwear.di

import android.app.Activity
import android.content.Context
import android.media.AudioFormat.CHANNEL_IN_DEFAULT
import android.media.AudioFormat.ENCODING_PCM_16BIT
import android.media.AudioRecord
import android.media.AudioRecord.getMinBufferSize
import android.media.MediaRecorder.AudioSource.DEFAULT
import be.tarsos.dsp.pitch.Yin
import com.lbbento.pitchuptuner.audio.AudioRecordHelper.Companion.getReadSize
import com.lbbento.pitchuptuner.audio.AudioRecordHelper.Companion.getSampleRate
import com.lbbento.pitchuptuner.audio.AudioRecordWrapper
import com.lbbento.pitchuptuner.service.TunerService
import com.lbbento.pitchuptuner.service.TunerServiceImpl
import com.lbbento.pitchuptuner.service.pitch.PitchHandler
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(val activity: Activity) {

    @Provides
    fun context(): Context = activity.baseContext

    @Provides
    fun activityBase(): Activity = activity

    @Provides
    fun provideAudioRecord(): AudioRecord = AudioRecord(DEFAULT,
            getSampleRate(),
            CHANNEL_IN_DEFAULT,
            ENCODING_PCM_16BIT,
            getMinBufferSize(getSampleRate(), CHANNEL_IN_DEFAULT, ENCODING_PCM_16BIT))

    @Provides
    fun provideTunerService(audioRecord: AudioRecordWrapper, torsoYin: Yin, pitchHandler: PitchHandler): TunerService = TunerServiceImpl(audioRecord, torsoYin, pitchHandler)

    @Provides
    fun provideTorsoYin(): Yin = Yin(getSampleRate().toFloat(), getReadSize())
}