[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)
[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
![Travis-ci](https://travis-ci.org/lbbento/pitchup.svg)
<br><br><a href='https://play.google.com/store/apps/details?id=com.lbbento.pitchup'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/badge_new.png'/></a>

# PitchUp
PitchUp is an open source project that provides a tuner for instruments(for now only guitars) based on pitch using the smartphone or smartwatch microphone.
The aim of the project is to get people to contribute towards the core development of the library adding new instruments and improving the algoritm to detect tunes.

The project consists of 4 main components:

* The PitchUp core library which is the responsible for getting a raw pitch and return the note correspondent to it depending on the kind of instrument.
* The PitchUp android tuner module which wraps the logic to record audio on android based devices and uses the core library internally.
* The Android app also available in [Google Play](https://play.google.com/store/apps/details?id=com.lbbento.pitchup)
* The Wear App that is shipped with the Android App, but is also available as a standalone WearApp in the playstore. [coming soon!]

## PitchUp Core  <a href='https://bintray.com/lbbento/pitchup/core/_latestVersion'><img src='https://api.bintray.com/packages/lbbento/pitchup/core/images/download.svg'></a></h2>

#### How to include the library in your project:
```gradle
compile 'com.lbbento.pitchup:core:1.0.0'
```
Usage:
```kotlin
//Create a new PitchHandler object
val pitchHandler = PitchHandler(InstrumentType.GUITAR)

//Call handle pitch to obtain a musical note from a given pitch
val pitchResult = pitchHandler.handlePitch(somePitchFloatValue)

//Do something with the value returned
doSomethingWithResult(pitchResult)
```

## PitchUp Android Tuner  <a href='https://bintray.com/lbbento/pitchup/tuner/_latestVersion'><img src='https://api.bintray.com/packages/lbbento/pitchup/tuner/images/download.svg'></a></h2>

#### How to include the library in your project:
```gradle
compile 'com.lbbento.pitchup:tuner:1.0.0'
```

Usage:

The android module provides a listener that can be used as bellow:

```kotlin
//Create audio recorder
val audioRecorder = PitchAudioRecorder(AudioRecord(MediaRecorder.AudioSource.DEFAULT,
        44100,
        AudioFormat.CHANNEL_IN_DEFAULT,
        AudioFormat.ENCODING_PCM_16BIT,
        AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT)))


//Create listener
val guitarTunerListener = object: GuitarTunerListener {

    override fun onNoteReceived(tunerResult: TunerResult) {
        doSomethingWithResult(tunerResult)
    }

    override fun onError(e: Throwable) {
        showError(e)
    }
}

//Start listening to Guitar tuner
val guitarTuner = GuitarTuner(audioRecorder, guitarTunerListener)
guitarTuner.start()

//Stop listening
guitarTuner.stop()
```

If you prefer, you can also use the Rx interface:

```kotlin

//Create audio recorder
val audioRecorder = PitchAudioRecorder(AudioRecord(MediaRecorder.AudioSource.DEFAULT,
    44100,
    AudioFormat.CHANNEL_IN_DEFAULT,
    AudioFormat.ENCODING_PCM_16BIT,
    AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT)))

//Guitar tuner reactive interface - RxJava
val guitarTunerReactive = GuitarTunerReactive(audioRecorder)

//Subscribe to start listening for notes
guitarTunerReactive.listenToNotes()
    .subscribeOn(appSchedulers.io())
    .observeOn(appSchedulers.ui())
    .subscribe(
        { tunerResult -> doSomethingWithResult(tunerResult) },
        { e -> showError(e) }
    )
    
```

#### The tuner result contains the following information:

Property|Type|Definition
--- | --- | ---
note|String|A, A#, B etc.
tuningStatus|TuningStatus|DEFAULT(silence or out of range) - TUNED - TOO_LOW - TOO_HIGH, etc.
expectedFrequency|Double|The expected frequency for the closer note given the pitch. 
diffFrequency|Double|Difference from the pitch given to the expected frequency.
diffCents|Double|Difference in cents from the current frequency to the expected frequency. 

### Privacy Policy

This page informs you of our policies regarding the collection, use and disclosure of Personal Information when you use our Service.

PitchUp don't store or share any kind of information when tuning an instrument. No data is saved or shared through any means. 

Contact Us

If you have any questions about this Privacy Policy, please contact me at lucasbento7[at]gmail.com
