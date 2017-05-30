package com.lbbento.pitchuptuner.service

interface TunerService {
    fun getNotes(): rx.Observable<TunerResult>
}