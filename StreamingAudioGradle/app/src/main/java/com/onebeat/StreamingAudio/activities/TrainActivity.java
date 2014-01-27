package com.onebeat.StreamingAudio.activities;

import com.onebeat.StreamingAudio.R;
import com.onebeat.StreamingAudio.helpers.AudioHelperBase;

import android.app.Activity;
import android.content.res.Resources;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import java.io.File;

/** MainActivity activity mimics the audio streaming,
 * instead of streaming audio data on network,
 * it reads data from local file
 * The main purpose is to show how to play data 
 * buffer in chunks with AudioTrack class */
public class TrainActivity extends Activity implements OnItemSelectedListener {
    AudioHelperBase aH;
    ListView musiclist;
    int music_column_index;
    int count;
    TextView selection;
    Spinner mspinner;
    String[] mStrings;
    File file[];

    Cursor cursor;
    MediaPlayer mMediaPlayer;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train);
        init_mspinner();
        final Button btnPlayPause = (Button)findViewById(R.id.btnPlayPause);
        btnPlayPause.setOnClickListener(new OnClickListener() {
            boolean BState = true;

            @Override
            public void onClick(View v) {
                if (BState == true) {
                    aH.play();
                    btnPlayPause.setBackgroundResource(R.drawable.pausebutton);
                    BState = false;
                } else {
                    aH.stop();
                    btnPlayPause.setBackgroundResource(R.drawable.playbutton);
                    BState = true;
                }
            }
        });
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        //aH = new AudioHelperBase(this, "lanegra.wav");
    }

    private void init_mspinner() {

        String path = Environment.getExternalStorageDirectory().toString()+"/Music";
        Log.d("Files", "Path: " + path);

        File f = new File(path);
        file = f.listFiles();

        Log.d("Files", "Size: "+ file.length);

        mStrings = new String[file.length];
        for (int i=0; i < file.length; i++)
        {
            Log.d("Files", "FileName:" + file[i].getName());
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
    public void onItemSelected(AdapterView<?> parent, View v, int position,
                               long id) {
        // TODO Auto-generated method stub
        //selection.setText(mStrings[position]);
        aH = new AudioHelperBase(this, file[position]);

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
        //selection.setText("");
    }
}
