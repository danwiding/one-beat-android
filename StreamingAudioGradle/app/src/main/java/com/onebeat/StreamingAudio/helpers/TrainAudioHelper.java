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
        this.beatFile = new beatFileModel();
        this.beatFile.setFileLocation(beatFileName);
    }

    public void tap(int beatNum){
        int markerPos = this.track.getPlaybackHeadPosition();
        this.beatFile.beats.add(new beatModel(markerPos, beatNum));
    }

    public void write(){
        this.beatFile.writeFile(this.beatFile.getFileLocation());
    }
}
