package com.onebeat.StreamingAudio.helpers;

import android.content.Context;
import android.media.AudioTrack;
import android.util.Log;

import com.onebeat.StreamingAudio.models.beatFileModel;
import com.onebeat.StreamingAudio.models.beatModel;

public class TrainAudioHelper extends AudioHelperBase{
    beatFileModel beatFile;

    public TrainAudioHelper(Context context, String fileName, String beatFileName) {
        super(context, fileName);
        this.beatFile = new beatFileModel(beatFileName);
    }

    public void tap(int beatNum){
        Log.i("TrainHelperBase::tap", "Tap!");
        int markerPos = this.track.getPlaybackHeadPosition();
        this.beatFile.addBeat(markerPos, beatNum);
    }

    public void write(){
        this.beatFile.writeFile();
    }
}
