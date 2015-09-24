package com.cs4720.ms1;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class DisplayCoords extends AppCompatActivity implements
        ConnectionCallbacks, OnConnectionFailedListener {

    protected GoogleApiClient googleApiClient;
    protected Location lastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_coords);
        buildGoogleApiClient();
    }

    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle connectionHint) {

        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null) {
            TextView display = (TextView) findViewById(R.id.display);
            display.setTextSize(20);
            Intent intent = getIntent();
            String name = intent.getStringExtra(MainActivity.NAME);
            String text = "Hello "+name+". Your location is (" +String.valueOf(lastLocation.getLatitude())+","+String.valueOf(lastLocation.getLongitude())+")";
            display.setText(text);
        }
    }
    @Override
    public void onConnectionFailed(ConnectionResult result) {

    }


    @Override
    public void onConnectionSuspended(int cause) {
        googleApiClient.connect();
    }
}