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
    public ArrayList<beatModel> beats;
    private String fileLocation;

    public beatFileModel(){
        this.beats = new ArrayList<beatModel>();
    }

    public static beatFileModel loadFile(String fileName) {
        beatFileModel beatFile = new beatFileModel();
        beatFile.fileLocation = fileName;
        CSVReader reader = null;
        List rawBeatList = null;
        try {
            reader = new CSVReader(new FileReader(fileName));
            rawBeatList = reader.readAll();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
            }
        }
        for (Object object : rawBeatList) {
            String[] row = (String[]) object;
            beatModel beat = new beatModel(Integer.parseInt(row[0]), Integer.parseInt(row[1]));
            beatFile.beats.add(beat);
        }
        return beatFile;
    }

    public void writeFile(String fileName){
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(fileName, false));
            for (beatModel beat : this.beats){
                String[] beatLine = {String.valueOf(beat.markerPos), String.valueOf(beat.beatNum)};
                writer.writeNext(beatLine);
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
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
}
