package com.cs4720.ms1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Brian on 11/27/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME_EVENTS = "events";
    public static final String TABLE_NAME_COORDS = "coords";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_START = "start";
    public static final String COLUMN_NAME_END = "end";
    public static final String COLUMN_NAME_COORDS = "coords";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_LONGITUDE = "longitude";
    public static final String COLUMN_NAME_LATITUDE = "latitude";

    /*private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +

            " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;*/

    public DBHelper(Context context) {
        super(context, "events", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS events(name VARCHAR(25), start BIGINT, end BIGINT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean createEvent(String name, long start, long end){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * from events WHERE name=" + "'" + name + "'", null);
        if(c.getCount() > 0){
            return false;
        }
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("start",start);
        values.put("end", end);
        db = this.getWritableDatabase();
        db.insert("events", null, values);
        db.execSQL("CREATE TABLE IF NOT EXISTS coords" + name + "(time BIGINT, latitude FLOAT, longitude FLOAT);");
        return true;
    }

    public EventTracker getEvent(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * from events WHERE name=" + "'" + name + "'", null);
        c.moveToFirst();
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTimeInMillis(c.getLong(1));
        end.setTimeInMillis(c.getLong(2));
        ArrayList<Coord> coords = new ArrayList<>();
        c = db.rawQuery("SELECT * from coords"+name, null);
        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++){
            long time = c.getLong(0);
            double lat = c.getDouble(1);
            double lon = c.getDouble(2);
            coords.add(new Coord(lat,lon, time));
            c.moveToNext();
        }
        return new EventTracker(name, start, end, coords);
    }

    public ArrayList<EventTracker> getAllEvents(){
        ArrayList<EventTracker> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor eventData = db.rawQuery("SELECT * from events", null);
        eventData.moveToFirst();
        for(int i = 0; i < eventData.getCount(); i++) {
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            start.setTimeInMillis(eventData.getLong(1));
            end.setTimeInMillis(eventData.getLong(2));
            ArrayList<Coord> coords = new ArrayList<>();
            Cursor coordData = db.rawQuery("SELECT * from coords" + eventData.getString(0), null);
            coordData.moveToFirst();
            for (int j = 0; j < coordData.getCount(); j++) {
                long time = coordData.getLong(0);
                double lat = coordData.getDouble(1);
                double lon = coordData.getDouble(2);
                coords.add(new Coord(lat, lon, time));
            }
            events.add(new EventTracker(eventData.getString(0), start, end, coords));
            eventData.moveToNext();
        }
        return events;
    }
    public void writeCoords(String name, ArrayList<Coord> coords){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for(int i = 0; i < coords.size(); i++) {
            Coord c = coords.get(i);
            values.put("time", c.time);
            values.put("latitude", c.lat);
            values.put("longitude", c.lon);
            db.insert("coords" + name, null, values);
        }
    }
}
