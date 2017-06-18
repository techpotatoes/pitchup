package com.lbbento.pitchuptuner

import com.lbbento.pitchuptuner.service.TunerResult

interface GuitarTunerListener {
    fun onNoteReceived(tunerResult: TunerResult)
    fun onError(throwable: Throwable)
}