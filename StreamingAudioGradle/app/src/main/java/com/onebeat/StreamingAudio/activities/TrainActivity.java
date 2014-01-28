package com.onebeat.StreamingAudio.activities;

import com.onebeat.StreamingAudio.R;
import com.onebeat.StreamingAudio.helpers.TrainAudioHelper;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TrainActivity extends Activity {
    TrainAudioHelper trainer;
    String wavName, csvName;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train);

        Intent intent = getIntent();
        wavName = intent.getExtras().getString("wavName");
        csvName = intent.getExtras().getString("csvName");

        final Button btnPlayPause = (Button)findViewById(R.id.btnPlayPause);
        btnPlayPause.setOnClickListener(new OnClickListener() {
            boolean BState = true;

            @Override
            public void onClick(View v) {
                if (BState == true) {
                    PlayAudio();
                    btnPlayPause.setBackgroundResource(R.drawable.pausebutton);
                    BState = false;
                } else {
                    StopAudio();
                    btnPlayPause.setBackgroundResource(R.drawable.playbutton);
                    BState = true;
                }
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

        trainer = new TrainAudioHelper(this, wavName, csvName);

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
