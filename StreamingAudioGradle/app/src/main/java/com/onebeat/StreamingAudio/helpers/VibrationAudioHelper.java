package com.onebeat.StreamingAudio.helpers;

import com.onebeat.StreamingAudio.models.beatFileModel;
import android.content.Context;
import android.media.AudioTrack;
import android.media.AudioTrack.OnPlaybackPositionUpdateListener;
import android.os.Vibrator;
import android.util.Log;

public class VibrationAudioHelper extends AudioHelperBase {
    beatFileModel beatFile;
    int nextBeatIndex;
    Vibrator myVibrator;
    Thread vibrateThread;

    public VibrationAudioHelper(Context context, String fileName, String beatFileName) {
        super(context, fileName);

        // load beat file
        this.beatFile = new beatFileModel(beatFileName);
        this.beatFile.loadFile();
        this.nextBeatIndex = 0;

        // Get instance of Vibrator from current Context
        myVibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

        vibrateThread = new Thread(new Runnable() {
            public void run() {
                try {
                    if (beatFile.getNumVibs() > nextBeatIndex){
                        track.setNotificationMarkerPosition(beatFile.getMarkerPos(nextBeatIndex++));
                    }
                    else{
                        track.setNotificationMarkerPosition((int)fileSize);
                    }
                    track.setPlaybackPositionUpdateListener(new OnPlaybackPositionUpdateListener() {
                        @Override
                        public void onMarkerReached(AudioTrack track) {
                            if (beatFile.getNumVibs()  > nextBeatIndex){
                                myVibrator.vibrate(50);
                                Log.d("AudioHelperBase::vibrateThread::onMarkerReached", "Vibrate!");
                                track.setNotificationMarkerPosition(beatFile.getMarkerPos(nextBeatIndex++));
                            }
                            else if (track.getPlaybackHeadPosition() <= fileSize){
                                track.setNotificationMarkerPosition((int)fileSize);
                            }
                            else{
                                stop();
                                Log.d("AudioHelperBase", "Audio track end of file reached...");
                            }
                        }

                        @Override
                        public void onPeriodicNotification(AudioTrack track) {

                        }
                    });
                }
                catch (Throwable t) {
                    Log.i("vibrateThread", "Thread  exception "+t);
                }
            }
        });

        vibrateThread.start();
    }
}