package com.onebeat.StreamingAudio.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.onebeat.StreamingAudio.R;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button btnTrain = (Button)findViewById(R.id.btnTrain);
        btnTrain.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// launch Training Activity
				Intent myIntent = new Intent(MainActivity.this, TrainActivity.class);
//				myIntent.putExtra("key", value); //Optional parameters
				MainActivity.this.startActivity(myIntent);
			}
		});

        Button btnPlayVibrate = (Button)findViewById(R.id.btnPlayVibrate);
        btnPlayVibrate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // launch Training Activity
                Intent myIntent = new Intent(MainActivity.this, VibrationPlayActivity.class);
//				myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });
    }
}
