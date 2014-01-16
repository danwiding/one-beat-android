package com.onebeat.StreamingAudio;

import java.io.FileInputStream;
import java.io.IOException;
import com.onebeat.StreamingAudio.R;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/** Main activity mimics the audio streaming, 
 * instead of streaming audio data on network,
 * it reads data from local file
 * The main purpose is to show how to play data 
 * buffer in chunks with AudioTrack class */
public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btnStream = (Button)findViewById(R.id.btnStream);
        btnStream.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PlayAudio();
			}
		});
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }
    
    private void PlayAudio()
    {
    	AssetFileDescriptor descriptor = null;
       	FileInputStream audioStream = null;
    	long fileSize = 0;
    	try {
    		descriptor = getAssets().openFd("ss.wav");
			audioStream = descriptor.createInputStream();
			fileSize = audioStream.getChannel().size();
    	} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	 
    	long bytesWritten = 0;
    	int bytesRead = 0;
    	int bufferSize = 0;
    	byte[] buffer;
    	AudioTrack track;
		
		bufferSize = 8000;
    	buffer = new byte[bufferSize];
    	track = new AudioTrack(AudioManager.STREAM_MUSIC, 8000, 
 				AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, 
 				bufferSize, AudioTrack.MODE_STREAM);
    	
    	// in stream mode, 
    	//   1. start track playback
    	//   2. write data to track
    	track.play(); 
    	
    	while(bytesWritten < fileSize)
    	{
    		try {
    			bytesRead = audioStream.read(buffer, 0, bufferSize);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		bytesWritten += track.write(buffer, 0, bytesRead);
    		// debug. spit out total bytes written to logcat
    		Log.d("PlayAudio", "bytesWritten: " + bytesWritten);
    	}
    }
    
}
