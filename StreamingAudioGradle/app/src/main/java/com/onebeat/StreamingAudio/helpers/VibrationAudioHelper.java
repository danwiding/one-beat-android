package com.onebeat.StreamingAudio.helpers;

import com.onebeat.StreamingAudio.models.beatFileModel;
import android.content.Context;
import android.media.AudioTrack;
import android.media.AudioTrack.OnPlaybackPositionUpdateListener;
import android.os.Vibrator;
import android.util.Log;

public class VibrationAudioHelper extends AudioHelperBase implements OnPlaybackPositionUpdateListener {
    beatFileModel beatFile;
    int nextBeatIndex;
    Vibrator myVibrator;

    public VibrationAudioHelper(Context context, String fileName, String beatFileName) {
        super(context, fileName);
        this.beatFile = new beatFileModel(beatFileName);
        this.beatFile.loadFile();
        this.nextBeatIndex = 0;
        if (this.beatFile.getNumVibs() > this.nextBeatIndex){
            track.setNotificationMarkerPosition(beatFile.getMarkerPos(this.nextBeatIndex++));
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
        if (this.beatFile.getNumVibs()  > this.nextBeatIndex){
            // Vibrate for 400 milliseconds
            myVibrator.vibrate(100);
            Log.d("AudioHelperBase::onMarkerReached", "Vibrate!");
            track.setNotificationMarkerPosition(beatFile.getMarkerPos(this.nextBeatIndex++));
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