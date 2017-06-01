package com.lbbento.pitchuptuner.service

import android.media.AudioRecord.RECORDSTATE_RECORDING
import be.tarsos.dsp.pitch.Yin
import com.lbbento.pitchuptuner.audio.PitchAudioRecorder
import com.lbbento.pitchuptuner.service.pitch.PitchHandler
import rx.Observable.create


class TunerServiceImpl @javax.inject.Inject constructor(private val pitchAudioRecord: PitchAudioRecorder, private val torsoYin: Yin, private val pitchHandler: PitchHandler) : TunerService {

    override fun getNotes(): rx.Observable<TunerResult> {
        return create<TunerResult>({
            try {
                pitchAudioRecord.startRecording()

                while (pitchAudioRecord.recordingState == RECORDSTATE_RECORDING) {
                    val buffer = pitchAudioRecord.read()

                    val pitchResult = torsoYin.getPitch(buffer)

                    val result = pitchHandler.handlePitch(pitchResult.pitch)

                    it.onNext(TunerResult(note = result.note, tunningStatus = result.tunningStatus, diffFrequency = result.diffFrequency, expectedFrequency = result.expectedFrequency))
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