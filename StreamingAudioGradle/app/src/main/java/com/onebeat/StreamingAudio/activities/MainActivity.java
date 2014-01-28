package com.onebeat.StreamingAudio.activities;

import com.onebeat.StreamingAudio.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.File;

public class MainActivity extends Activity implements OnItemSelectedListener {
    TextView selection;
    Spinner mspinner;
    File file[];
    String[] mStrings;
    String wavName, csvName;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        init_mspinner();

        Button btnTrain = (Button)findViewById(R.id.btnTrain);
        btnTrain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// launch Training Activity
				Intent myIntent = new Intent(MainActivity.this, TrainActivity.class);
				myIntent.putExtra("wavName", wavName);
                myIntent.putExtra("csvName", csvName);
				MainActivity.this.startActivity(myIntent);
			}
		});

        Button btnPlayVibrate = (Button)findViewById(R.id.btnPlayVibrate);
        btnPlayVibrate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // launch Training Activity
                Intent myIntent = new Intent(MainActivity.this, VibrationPlayActivity.class);
                myIntent.putExtra("wavName", wavName);
                myIntent.putExtra("csvName", csvName);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }

    private void init_mspinner() {

        String path = Environment.getExternalStorageDirectory().toString()+"/Music";

        File f = new File(path);
        file = f.listFiles();

        mStrings = new String[file.length];
        for (int i=0; i < file.length; i++)
        {
            mStrings[i] = file[i].getName();
        }

        selection = (TextView) findViewById(R.id.selection);
        mspinner = (Spinner) findViewById(R.id.SongSelect);

        mspinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mStrings);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //selection.setText(mStrings[position]);
        wavName = file[i].getPath();
        csvName = wavName.substring(0,wavName.length()-3) + "csv";
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
