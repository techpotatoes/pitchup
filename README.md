[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)
[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
![Travis-ci](https://travis-ci.org/skyguydaa7/pitchup.svg)
<p>&nbsp;</p>
<h1>PitchUp</h1>
<p>PitchUp is an open source project that provides a tuner for instruments(for now only guitars) based on pitch using the smartphone or smartwatch microphone.</p>
<p>The aim of the project is to get people to contribute towards the core development of the library adding new instruments and improving the algoritm to detect tunes.&nbsp;</p>
<p>The project consists of 4 main components:</p>
<ul>
<li>The PitchUp core library which is the responsible for getting a raw pitch and return the note correspondent to it depending on the kind of instrument.</li>
<li>The PitchUp android tuner module which wraps the logic to record audio on android based devices and uses the core library internally.&nbsp;</li>
<li>The Android app also available in google play: [GOOGLE PLAY LINK]</li>
<li>The Wear App that is shipped with the Android App, but is also available as a standalone WearApp in the playstore.&nbsp;</li>
</ul>
<p>&nbsp;</p>
<h2><strong>PitchUpCore</strong>       <a href='https://bintray.com/lbbento/pitchup/core/_latestVersion'><img src='https://api.bintray.com/packages/lbbento/pitchup/core/images/download.svg'></a></h2>
<h4>How to include library in your project:</h4>
<div class="highlight highlight-source-groovy"><pre>compile <span class="pl-s"><span class="pl-pds">'</span>com.lbbento.pitchup:core:1.0.0<span class="pl-pds">'</span></span></pre></div>
<p>Usage:</p>
<p>TODO</p>
<p>&nbsp;</p>
<h2>PitchUpAndroid       <a href='https://bintray.com/lbbento/pitchup/tuner/_latestVersion'><img src='https://api.bintray.com/packages/lbbento/pitchup/tuner/images/download.svg'></a></h2>
<h4>How to include library in your project:</h4>
<div class="highlight highlight-source-groovy"><pre>compile <span class="pl-s"><span class="pl-pds">'</span>com.lbbento.pitchup:tuner:1.0.0<span class="pl-pds">'</span></span></pre></div>
<p>Usage:</p>
<p>The android module provides a listener that can be used as bellow:</p>
<p>TODO</p>
<p>If you prefer, you can also use the Rx interface:</p>
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
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
