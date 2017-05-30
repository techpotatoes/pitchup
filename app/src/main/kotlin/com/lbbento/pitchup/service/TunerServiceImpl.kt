package com.lbbento.pitchup.service

import be.tarsos.dsp.pitch.Yin
import com.lbbento.pitchup.audio.AudioRecordWrapper
import rx.Observable
import rx.Observable.create
import javax.inject.Inject


class TunerServiceImpl @Inject constructor(val audioRecord: AudioRecordWrapper, val torsoYin: Yin) : TunerService {

    override fun getNotes(): Observable<TunerResult> {
        return create<TunerResult>({
            audioRecord.startRecording()

            val buffer = audioRecord.read()
        })
    }
}