package com.onebeat.StreamingAudio.helpers;

import android.content.res.AssetFileDescriptor;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AudioHelperBase {
    AudioTrack track;
    FileInputStream audioStream = null;
    byte[] buffer;
    int bufferSize = 0;
    Thread transferThread;
    boolean stopFlag = false;

    public AudioHelperBase(Context context, File fileName) {

        /* OPEN FILE */
        //AssetFileDescriptor descriptor = null;
        long fileSize = 0;
        try {
            //descriptor = context.getAssets().openFd(fileName);
            //audioStream = descriptor.createInputStream();
            audioStream = new FileInputStream(fileName);
            fileSize = audioStream.getChannel().size();
            Log.d("AudioHelperBase", "file size is " + fileSize);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        /* INITIALIZE BUFFER AND STREAM */
        bufferSize = AudioTrack.getMinBufferSize(44100, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
        buffer = new byte[bufferSize];
        track = new AudioTrack(AudioManager.STREAM_MUSIC, 44100,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
                bufferSize, AudioTrack.MODE_STREAM);

        /* SETUP CALLBACKS */
        track.setNotificationMarkerPosition(safeLongToInt(fileSize));
        track.setPlaybackPositionUpdateListener(new AudioTrack.OnPlaybackPositionUpdateListener() {
            @Override
            public void onPeriodicNotification(AudioTrack track) {
                // nothing to do
            }
            @Override
            public void onMarkerReached(AudioTrack track) {
                stop();
                Log.d("AudioHelperBase", "Audio track end of file reached...");
            }
        });
    }

    Runnable transferAudio = new Runnable()
    {
        public void run()
        {
            long bytesWritten = 0;
            int bytesRead = 0;

            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

            while(!stopFlag)
            {
                try {
                    bytesRead = audioStream.read(buffer, 0, bufferSize);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                bytesWritten += track.write(buffer, 0, bytesRead);
                // debug. spit out total bytes written to logcat
                Log.d("AudioHelperBase::transferAudio", "bytesRead: " + bytesRead + " bytesWritten: " + bytesWritten);
            }
        }
    };

    public void play() {
        stopFlag = false;

        track.play();

        transferThread = new Thread(transferAudio);
        transferThread.start();
    }

    public void pause() {
        stopFlag = true;
        track.pause();
    }

    public void stop() {
        stopFlag = true;
        track.pause();
    }

    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }
}
