package com.lbbento.pitchup.service

import rx.Observable

interface TunerService {
    fun getNotes(): Observable<Void>
}