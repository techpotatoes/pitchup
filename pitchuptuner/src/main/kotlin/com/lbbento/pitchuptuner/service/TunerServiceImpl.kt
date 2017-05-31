package com.lbbento.pitchuptuner.service

import android.media.AudioRecord.RECORDSTATE_RECORDING
import be.tarsos.dsp.pitch.Yin
import com.lbbento.pitchuptuner.audio.AudioRecordWrapper
import com.lbbento.pitchuptuner.service.pitch.PitchHandler
import rx.Observable.create


class TunerServiceImpl @javax.inject.Inject constructor(private val audioRecord: AudioRecordWrapper, private val torsoYin: Yin, private val pitchHandler: PitchHandler) : TunerService {

    override fun getNotes(): rx.Observable<TunerResult> {
        return create<TunerResult>({
            try {
                audioRecord.startRecording()

                while (audioRecord.recordingState == RECORDSTATE_RECORDING) {
                    val buffer = audioRecord.read()

                    val pitchResult = torsoYin.getPitch(buffer)

                    val result = pitchHandler.handlePitch(pitchResult.pitch)

                    it.onNext(TunerResult(note = result.note, tunningStatus = result.tunningStatus, diff = result.diff))
                }

                it.onCompleted()

            } catch (e: IllegalStateException) {
                it.onError(IllegalStateException("An error has occurred when trying to record audio. Check your permissions."))
            } catch (e: Exception) {
                it.onError(UnknownError("Unexpected error"))
            }
        })
    }
}