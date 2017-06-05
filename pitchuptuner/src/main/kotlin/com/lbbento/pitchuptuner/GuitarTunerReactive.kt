package com.lbbento.pitchuptuner

import com.lbbento.pitchuptuner.audio.PitchAudioRecorder
import com.lbbento.pitchuptuner.service.TunerResult
import com.lbbento.pitchuptuner.service.TunerService
import rx.Observable
import java.util.concurrent.TimeUnit.MILLISECONDS

class GuitarTunerReactive(private val pitchAudioRecord: PitchAudioRecorder) {

    private val tunerService: TunerService by lazy { initializeTunerService(pitchAudioRecord) }

    fun listenToNotes(): Observable<TunerResult> = tunerService.getNotes().sample(400, MILLISECONDS)

    private fun initializeTunerService(pitchAudioRecord: PitchAudioRecorder) = TunerService(pitchAudioRecord)
}