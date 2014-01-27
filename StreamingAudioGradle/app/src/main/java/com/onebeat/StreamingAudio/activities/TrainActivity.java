package com.onebeat.StreamingAudio.activities;

import com.onebeat.StreamingAudio.R;
import com.onebeat.StreamingAudio.helpers.AudioHelperBase;
import com.onebeat.StreamingAudio.helpers.TrainAudioHelper;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/** MainActivity activity mimics the audio streaming,
 * instead of streaming audio data on network,
 * it reads data from local file
 * The main purpose is to show how to play data 
 * buffer in chunks with AudioTrack class */
public class TrainActivity extends Activity {
    TrainAudioHelper trainer;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train);
        Button btnStream = (Button)findViewById(R.id.btnStream);
        btnStream.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayAudio();
            }
        });
        Button btnStop = (Button)findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                StopAudio();
            }
        });
        Button btnTap = (Button)findViewById(R.id.btnTap);
        btnTap.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                TapAudio();
            }
        });

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        trainer = new TrainAudioHelper(this, "lanegra.wav", "lanegra.csv");
    }
    
    private void PlayAudio()
    {
        trainer.play();
    }

    private void StopAudio()
    {
        trainer.stop();
        trainer.write();
    }

    private void TapAudio()
    {
        trainer.tap(1);
    }
}
