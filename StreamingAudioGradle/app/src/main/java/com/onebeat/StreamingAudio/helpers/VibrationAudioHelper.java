package com.onebeat.StreamingAudio.helpers;

import android.content.Context;
import android.media.AudioTrack;
import android.os.Vibrator;
import android.util.Log;

import com.onebeat.StreamingAudio.models.beatFileModel;

public class VibrationAudioHelper extends AudioHelperBase implements AudioTrack.OnPlaybackPositionUpdateListener {
    beatFileModel beatFile;
    int nextBeatIndex;
    Vibrator myVibrator;

    public VibrationAudioHelper(Context context, String fileName) {
        super(context, fileName);
    }

    public VibrationAudioHelper(Context context, String fileName, String beatFileName) {
        super(context, fileName);
        this.beatFile = beatFileModel.loadFile(beatFileName);
        this.nextBeatIndex = 0;
        if (beatFile.beats.size() > this.nextBeatIndex){
            track.setNotificationMarkerPosition(beatFile.beats.get(this.nextBeatIndex++).markerPos);
        }
        else{
            track.setNotificationMarkerPosition((int)this.fileSize);
        }
        track.setPlaybackPositionUpdateListener(this);

        // Get instance of Vibrator from current Context
        myVibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public void onMarkerReached(AudioTrack audioTrack) {
        if (beatFile.beats.size() > this.nextBeatIndex){
            // Vibrate for 400 milliseconds
            myVibrator.vibrate(200);
            track.setNotificationMarkerPosition(beatFile.beats.get(this.nextBeatIndex++).markerPos);
        }
        else if (track.getPlaybackHeadPosition() <= this.fileSize){
            track.setNotificationMarkerPosition((int)this.fileSize);
        }
        else{
            stop();
            Log.d("AudioHelperBase", "Audio track end of file reached...");
        }
    }

    @Override
    public void onPeriodicNotification(AudioTrack audioTrack) {

    }
}