package com.lbbento.pitchuptuner

import com.lbbento.pitchuptuner.audio.PitchAudioRecorder
import com.lbbento.pitchuptuner.service.TunerResult
import com.lbbento.pitchuptuner.service.TunerService
import rx.Observable

class GuitarTuner(private val pitchAudioRecord: PitchAudioRecorder) {

    private val tunerService: TunerService by lazy { initializeTunerService(pitchAudioRecord) }

    fun listenToNotes(): Observable<TunerResult> = tunerService.getNotes()

    private fun initializeTunerService(pitchAudioRecord: PitchAudioRecorder) = TunerService(pitchAudioRecord)
}