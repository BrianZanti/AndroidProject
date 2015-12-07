package com.cs4720.ms1;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Brian on 10/5/2015.
 */
public class EventTracker {

    private ArrayList<Coord> coordinates;
    private Calendar start;
    private Calendar end;
    private final String FILENAME = "gps_storage.txt";
    private String name = "";
    private boolean serviceLaunched;

    public EventTracker(){
        coordinates = new ArrayList<Coord>();
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        serviceLaunched = false;
    }

    public EventTracker(String name, Calendar start, Calendar end, ArrayList<Coord> coordinates){
        this.start = start;
        this.end = end;
        this.name = name;
        this.coordinates = coordinates;
    }

    public void setStartTime(Calendar startTime){
        start.set(start.get(Calendar.YEAR),start.get(Calendar.MONTH),start.get(Calendar.DAY_OF_MONTH),
                startTime.get(Calendar.HOUR_OF_DAY), startTime.get(Calendar.MINUTE));
    }

    public void setEndTime(Calendar endTime){
        end.set(end.get(Calendar.YEAR),end.get(Calendar.MONTH),end.get(Calendar.DAY_OF_MONTH),
                endTime.get(Calendar.HOUR_OF_DAY), endTime.get(Calendar.MINUTE));
    }

    public void setStartDate(Calendar startDate){
        start.set(startDate.get(Calendar.YEAR),startDate.get(Calendar.MONTH),startDate.get(Calendar.DAY_OF_MONTH),
                start.get(Calendar.HOUR_OF_DAY), start.get(Calendar.MINUTE));
    }

    public void setEndDate(Calendar endDate){
        end.set(endDate.get(Calendar.YEAR),endDate.get(Calendar.MONTH),endDate.get(Calendar.DAY_OF_MONTH),
                end.get(Calendar.HOUR_OF_DAY), end.get(Calendar.MINUTE));
    }

    public void setName(String name){
        String[] words = name.split(" ");
        name = "";
        for(int i = 0; i < words.length; i++)
        {
            name += words[i] + "_";
        }
        name = name.substring(0,name.length()-1);
        this.name = name;
    }

    public long getStartTime() {
        return start.getTimeInMillis();
    }

    public long getEndTime() {
        return end.getTimeInMillis();
    }

    public void save(Context context) throws IOException {

        String writeString = "";

        writeString += name + " " + String.valueOf(getStartTime()) + " " + String.valueOf(getEndTime()) + "\n";
        //fio.write(writeString, context);
    }

    public long getStartTimeInMillis(){
        return start.getTimeInMillis();
    }

    public long getEndTimeInMillis(){
        return end.getTimeInMillis();
    }

    public void setStartTimeInMillis(long start){
        this.start.setTimeInMillis(start);
    }

    public void setEndTimeInMillis(long end){
        this.end.setTimeInMillis(end);
    }

    public String getName(){
        return this.name;
    }

    public void launchService(){
        this.serviceLaunched = true;
    }

    public boolean isServiceLaunched(){
        return serviceLaunched;
    }

    public String[] getText(){
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("MM/dd/yyyy hh:mm a");
        SimpleDateFormat sdf2 = new SimpleDateFormat();
        sdf2.applyPattern("MM/dd/yyyy hh:mm a");
        String startString = "Start Time: "+sdf.format(start.getTime());
        String endString = "End Time: "+sdf2.format(end.getTime());
        String[] s = {name,startString,endString};
        return s;
    }

    public ArrayList<Coord> getCoords(){
        return this.coordinates;
    }
}
