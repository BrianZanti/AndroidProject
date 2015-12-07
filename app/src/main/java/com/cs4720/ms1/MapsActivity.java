package com.cs4720.ms1;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void alert(String alert) {
        Toast.makeText(this, alert, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        EventTracker e = new EventTracker();
        e = (new DBHelper(this)).getEvent(getIntent().getStringExtra("name"));
        ArrayList<Coord> coords = e.getCoords();
        for(int i = 0; i < coords.size(); i++) {
            Coord c = coords.get(i);
            LatLng ll = new LatLng(c.lat,c.lon);
            googleMap.addMarker(new MarkerOptions().position(ll).title(String.valueOf(c.time)));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(ll));
        }
    }
}
