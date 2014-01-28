package com.onebeat.StreamingAudio.models;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * Created by davidjun on 1/26/14.
 */
public class beatFileModel {
    public ArrayList<beatModel> beatsArray;
    private String fileLocation;

    public beatFileModel(String fileName){
        this.beatsArray = new ArrayList<beatModel>();
        this.fileLocation = fileName;
    }

    public void loadFile() {
        CSVReader reader = null;
        List rawBeatList = null;
        try {
            reader = new CSVReader(new FileReader(this.fileLocation));
            rawBeatList = reader.readAll();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
            }
        }
        for (Object object : rawBeatList) {
            String[] row = (String[]) object;
            addBeat(Integer.parseInt(row[0]), Integer.parseInt(row[1]));
        }
    }

    public void addBeat(int markerPos, int beatNum) {
        this.beatsArray.add(new beatModel(markerPos, beatNum));
    }

    public void writeFile(){
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(this.fileLocation, false));
            for (beatModel beat : this.beatsArray){
                String tmp = String.valueOf(beat.markerPos) + "#" + String.valueOf(beat.beatNum);
                writer.writeNext(tmp.split("#"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }
    }

    public String getFileLocation() {
        return this.fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public int getNumVibs() {
        return this.beatsArray.size();
    }

    public int getMarkerPos(int idx) {
        beatModel curbeat = this.beatsArray.get(idx);
        return curbeat.markerPos;
    }
}
