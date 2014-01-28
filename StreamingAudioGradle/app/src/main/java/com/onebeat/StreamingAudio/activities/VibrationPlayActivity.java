package com.onebeat.StreamingAudio.activities;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.onebeat.StreamingAudio.R;
import com.onebeat.StreamingAudio.helpers.TrainAudioHelper;
import com.onebeat.StreamingAudio.helpers.VibrationAudioHelper;

import java.io.File;

/** MainActivity activity mimics the audio streaming,
 * instead of streaming audio data on network,
 * it reads data from local file
 * The main purpose is to show how to play data 
 * buffer in chunks with AudioTrack class */
public class VibrationPlayActivity extends Activity {
    VibrationAudioHelper player;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vibrateplay);
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
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        File folder = new File(Environment.getExternalStorageDirectory() + "/BeatFiles");

        boolean var = false;
        if (!folder.exists())
            var = folder.mkdir();

        System.out.println("" + var);

        String filename = folder.toString() + "/" + "lanegra.csv";

        player = new VibrationAudioHelper(this, "lanegra.wav", filename);
    }
    
    private void PlayAudio()
    {
        player.play();
    }

    private void StopAudio()
    {
        player.stop();
    }
}
