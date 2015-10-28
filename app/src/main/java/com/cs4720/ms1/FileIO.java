package com.cs4720.ms1;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Created by Brian on 10/6/2015.
 */
public class FileIO {
    private String FILENAME = "gps_storage.txt";

    /*public FileIO(String filename) throws IOException {
        FILENAME = filename;
        File file = new File(FILENAME);
        if(!file.exists())
        {
            file.createNewFile();
        }
    }*/
    public FileIO() throws IOException {

    }

    public String[][] readFile(Context context) {
        try {
            File file = new File(FILENAME);
            /*if(!file.exists())
            {
                return null;
            }*/
            FileInputStream fis = context.openFileInput(FILENAME);
            byte[] input = new byte[fis.available()];
            String data_read = "";
            while (fis.read(input) != -1) {
                data_read += new String(input);
            }
            if (data_read.equals("")) {
                return null;
            }
            String[] lines = data_read.split("\\r?\\n");
            String[][] args = new String[lines.length][];
            for (int i = 0; i < lines.length; i++) {
                args[i] = lines[i].split(" ");
            }
            return args;
        }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean containsEventName(String eventName, Context context) throws IOException {
        String[][] args = readFile(context);
        if(args == null)
        {
            return false;
        }
        for(int i = 0; i < args.length; i++){
            if(args[i][0].equals(eventName)){
                return true;
            }
        }
        return false;
    }

    public void write(String data, Context context) throws IOException {
        FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_APPEND);
        byte[] b = data.getBytes();
        fos.write(b);
        fos.close();
    }

    public void clearDB(Context context) throws IOException {
        FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
        String s = "";
        fos.write(s.getBytes());
        fos.close();
        /*File file = new File(FILENAME);
        file.delete();*/
    }

    public ArrayList<EventTracker> getEvents(Context context) throws IOException {
        ArrayList<EventTracker> events = new ArrayList<>();
        String[][] data = readFile(context);
        if(data == null){
            return events;
        }
        for(int i = 0; i < data.length; i++){
            ArrayList<LatLng> coords = new ArrayList<>();
            for(int j = 3; j < data[i].length; j += 2)
            {
                LatLng longlat = new LatLng(Double.parseDouble(data[i][j].trim()), Double.parseDouble(data[i][j+1].trim()));
                coords.add(longlat);
            }
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            start.setTimeInMillis(Long.parseLong(data[i][1]));
            end.setTimeInMillis(Long.parseLong(data[i][2]));
            EventTracker e = new EventTracker(data[i][0],start, end,coords, false);
            events.add(e);
        }

        //String[][] s = readFile(context);
        return events;
    }

    public void writeCoords(String name, ArrayList<Location> coords, Context context) throws IOException {
        String[][] data = readFile(context);
        String writeData = "";
        for(int i = 0; i < data.length; i++){
            writeData += data[i][0] + " " + data[i][1] + " " + data[i][2];
            if(data[i][0].equals(name)){
                for(int j = 0; j < coords.size(); j++){
                    writeData += " " + String.valueOf(coords.get(j).getLatitude()) + " "
                        + String.valueOf(coords.get(j).getLongitude());
                }
            }
            writeData += "\n";
        }
        FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
        fos.write(writeData.getBytes());
        fos.close();

    }

    public EventTracker getEvent(Context context, String name){
        String[][] args = readFile(context);
        for(int i = 0; i < args.length; i++){
            if(args[i][0].equals(name)){
                Calendar start = Calendar.getInstance();
                start.setTimeInMillis(Long.parseLong(args[i][1]));
                Calendar end = Calendar.getInstance();
                end.setTimeInMillis(Long.parseLong(args[i][2]));
                ArrayList<LatLng> coords = new ArrayList<>();
                for(int j = 3; j < args[i].length; j+=2){
                    LatLng l = new LatLng(Double.parseDouble(args[i][j]),Double.parseDouble(args[i][j+1]));
                    coords.add(l);
                }
                EventTracker e = new EventTracker(args[i][0],start,end, coords,true);
                return e;
            }
        }
        return null;
    }
}