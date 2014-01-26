package com.onebeat.StreamingAudio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity {
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
				Intent myIntent = new Intent(Main.this, Train.class);
//				myIntent.putExtra("key", value); //Optional parameters
				Main.this.startActivity(myIntent);
			}
		});        
    }
}
