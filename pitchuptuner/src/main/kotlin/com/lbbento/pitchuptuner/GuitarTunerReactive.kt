package com.lbbento.pitchuptuner

import com.lbbento.pitchuptuner.audio.PitchAudioRecorder
import com.lbbento.pitchuptuner.service.TunerResult
import com.lbbento.pitchuptuner.service.TunerService
import rx.Observable
import java.util.concurrent.TimeUnit.MILLISECONDS

class GuitarTunerReactive(pitchAudioRecord: PitchAudioRecorder) {

    private var tunerService: TunerService

    init {
        tunerService = initializeTunerService(pitchAudioRecord)
    }

    internal constructor(pitchAudioRecord: PitchAudioRecorder, tunerService: TunerService) : this(pitchAudioRecord) {
        this.tunerService = tunerService
    }

    fun listenToNotes(): Observable<TunerResult> = tunerService.getNotes().throttleFirst(400, MILLISECONDS)

    private fun initializeTunerService(pitchAudioRecord: PitchAudioRecorder) = TunerService(pitchAudioRecord)
}