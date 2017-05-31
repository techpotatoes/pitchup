package com.lbbento.pitchupapp;

import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.lbbento.pitchuptuner.audio.AudioRecordHelper;
import com.lbbento.pitchuptuner.audio.AudioRecordWrapper;
import com.lbbento.pitchuptuner.service.TunerResult;
import com.lbbento.pitchuptuner.service.TunerServiceImpl;
import com.lbbento.pitchuptuner.service.pitch.PitchHandler;

import be.tarsos.dsp.pitch.Yin;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.media.AudioFormat.CHANNEL_IN_DEFAULT;
import static android.media.AudioFormat.ENCODING_PCM_16BIT;
import static android.media.AudioRecord.getMinBufferSize;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    //TESTING
    private AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.DEFAULT,
            AudioRecordHelper.Companion.getSampleRate(),
            CHANNEL_IN_DEFAULT,
            ENCODING_PCM_16BIT,
            getMinBufferSize(AudioRecordHelper.Companion.getSampleRate(), CHANNEL_IN_DEFAULT, ENCODING_PCM_16BIT));

    private AudioRecordWrapper audioRecordWrapper = new AudioRecordWrapper(audioRecord);

    private TunerServiceImpl tunerService = new TunerServiceImpl(audioRecordWrapper, new Yin(AudioRecordHelper.Companion.getSampleRate(), AudioRecordHelper.Companion.getReadSize()), new PitchHandler());

        @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.tuner);
//        requestPermissions(new String[]{RECORD_AUDIO}, 4);

        tunerService.getNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TunerResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        tunerResultError();
                    }

                    @Override
                    public void onNext(TunerResult tunerResult) {
                        tunerResultReceived(tunerResult);
                    }
                });


    }

    private void tunerResultReceived(TunerResult tunerResult) {
        switch (tunerResult.getTunningStatus()) {
            case TUNED: {
                String text = String.format("You are tuned to %s", tunerResult.getNote());
                Log.e("Lucas", text);
                this.text.setText(text);
                break;
            }
            case TOO_LOW: {
                String text = String.format("Almost tuned, a little up to %s", tunerResult.getNote());
                Log.e("Lucas", text);
                this.text.setText(text);
                break;
            }
            case TOO_HIGH: {
                String text = String.format("Almost tuned, a little down to %s", tunerResult.getNote());
                Log.e("Lucas", text);
                this.text.setText(text);
                break;
            }
            case WAY_TOO_LOW: {
                String text = String.format("Too flat! tune up a bit. Tuning %s", tunerResult.getNote());
                Log.e("Lucas", text);
                this.text.setText(text);
                break;
            }
            case WAY_TOO_HIGH:
                String text = String.format("Too sharp! tune down a bit. Tuning %s", tunerResult.getNote());
                Log.e("Lucas", text);
                this.text.setText(text);
                break;
            default: {
                Log.i("Lucas", ("DEFAULT STATE"));
                this.text.setText("DEFAULT STATE");
            }
        }
    }

    private void tunerResultError() {
        Log.e("Lucas", "Boom...");
    }
}
