package com.cs4720.ms1;

import android.widget.TextView;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity{


    public static final String NAME = "com.cs4720.NAME";
    protected Location lastLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public void onStart() {
        super.onStart();
        String FILENAME = "gps_storage.txt";
        TextView displayCoords = (TextView)findViewById(R.id.coords);

    }

    public void submitEventName(View view) throws IOException {
        EditText name = (EditText)findViewById(R.id.name_input);
        String eventName = name.getText().toString();
        TextView eventExists = (TextView)findViewById(R.id.eventExists);
        FileIO fio = new FileIO();
        eventExists.setText(String.valueOf(fio.containsEventName(eventName, getApplicationContext())));
    }


    public void alert(String alert) {
        Toast.makeText(this, alert, Toast.LENGTH_LONG).show();
    }


    public void startService(View view){
        Intent intent = new Intent(this, TrackCoords.class);
        startService(intent);
    }

    public void stopService(View view){
        Intent intent = new Intent(this, TrackCoords.class);
        stopService(intent);
    }

    public void setTimePress(View view){
        TimePickerFragment newFragment = new TimePickerFragment();
        FragmentManager f = getFragmentManager();
        newFragment.show(f, "timePicker");
    }

    public void launchCreateEvent(View view){
        Intent intent = new Intent(this, NewEvent.class);

        startActivity(intent);
    }

    public void clearDB(View view) throws IOException {
        FileIO fio = new FileIO();
        fio.clearDB(getApplicationContext());
        String[][] data = fio.readFile(getApplicationContext());
        String s;
    }
}