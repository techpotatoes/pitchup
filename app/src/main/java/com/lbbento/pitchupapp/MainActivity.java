package com.lbbento.pitchupapp;

import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lbbento.pitchuptuner.audio.AudioRecordHelper;
import com.lbbento.pitchuptuner.audio.AudioRecordWrapper;
import com.lbbento.pitchuptuner.service.TunerServiceImpl;

import be.tarsos.dsp.pitch.Yin;

import static android.media.AudioFormat.CHANNEL_IN_DEFAULT;
import static android.media.AudioFormat.ENCODING_PCM_16BIT;
import static android.media.AudioRecord.getMinBufferSize;

public class MainActivity extends AppCompatActivity {

    //TESTING
    private AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.DEFAULT,
            AudioRecordHelper.Companion.getSampleRate(),
            CHANNEL_IN_DEFAULT,
            ENCODING_PCM_16BIT,
            getMinBufferSize(AudioRecordHelper.Companion.getSampleRate(), CHANNEL_IN_DEFAULT, ENCODING_PCM_16BIT));

    private AudioRecordWrapper audioRecordWrapper = new AudioRecordWrapper(audioRecord);

    private TunerServiceImpl tunerService = new TunerServiceImpl(audioRecordWrapper, new Yin(AudioRecordHelper.Companion.getSampleRate(), AudioRecordHelper.Companion.getReadSize()));

    //    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        requestPermissions(new String[]{RECORD_AUDIO}, 4);

        tunerService.getNotes()
                .subscribe();
    }
}
