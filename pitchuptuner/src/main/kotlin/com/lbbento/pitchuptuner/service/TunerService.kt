package com.lbbento.pitchuptuner.service

import android.media.AudioRecord.RECORDSTATE_RECORDING
import be.tarsos.dsp.pitch.Yin
import com.lbbento.pitchuptuner.audio.PitchAudioRecorder
import com.lbbento.pitchuptuner.service.pitch.PitchHandler
import rx.Observable.create


open internal class TunerService(private val pitchAudioRecord: PitchAudioRecorder) {

    open val torsoYin: Yin by lazy { initializeYin(pitchAudioRecord) }
    open val pitchHandler: PitchHandler by lazy { initializePitchHandler() }

    fun getNotes(): rx.Observable<TunerResult> {
        return create<TunerResult>({
            try {
                pitchAudioRecord.startRecording()

                while (pitchAudioRecord.recordingState == RECORDSTATE_RECORDING && !it.isUnsubscribed) {
                    val buffer = pitchAudioRecord.read()

                    val pitchResult = torsoYin.getPitch(buffer)

                    val result = pitchHandler.handlePitch(pitchResult.pitch)

                    it.onNext(TunerResult(note = result.note, tunningStatus = result.tunningStatus, diffFrequency = result.diffFrequency, expectedFrequency = result.expectedFrequency, diffCents = result.diffCents))
                }

                it.onCompleted()

            } catch (e: IllegalStateException) {
                it.onError(IllegalStateException("An error has occurred when trying to record audio. Check your permissions."))
            } catch (e: Exception) {
                it.onError(UnknownError("Unexpected error"))
            } finally {
                pitchAudioRecord.stopRecording()
            }
        })
    }

    private fun initializeYin(pitchAudioRec: PitchAudioRecorder) = Yin(pitchAudioRec.sampleRateInHz.toFloat(), pitchAudioRec.readSize)

    private fun initializePitchHandler() = PitchHandler()
}