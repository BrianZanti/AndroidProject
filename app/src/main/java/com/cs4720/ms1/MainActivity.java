package com.cs4720.ms1;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
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

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EventTracker[] events;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //String[] events = {"1","2","3","4","6","12","34","12","11","13","2","2","3","4","6","12","34","12","11","13","2"};
        events = new EventTracker[0];
        try {
            events = (new FileIO()).getEvents(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(events);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, NewEvent.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        events = new EventTracker[0];
        try {
            events = (new FileIO()).getEvents(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < events.length; i++){
            EventTracker e = events[i];
            if(e.getEndTimeInMillis() > System.currentTimeMillis()
                    && !e.isServiceLaunched()){
                Intent intent = new Intent(this, MyService.class);
                Bundle b = new Bundle();
                b.putString("name", e.getName());
                b.putLong("end",e.getEndTimeInMillis());
                b.putLong("start",e.getStartTimeInMillis());
                intent.putExtra("bundle", b);
                startService(intent);
                e.launchService();
            }
        }

    }

    /*public void submitEventName(View view) throws IOException {
        EditText name = (EditText)findViewById(R.id.name_input);
        String eventName = name.getText().toString();
        TextView eventExists = (TextView)findViewById(R.id.eventExists);
        FileIO fio = new FileIO();
        eventExists.setText(String.valueOf(fio.containsEventName(eventName, getApplicationContext())));
    }*/

    public void alert(String alert) {
        Toast.makeText(this, alert, Toast.LENGTH_LONG).show();
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

    public void showEvents(View view){

        /*EventTracker[] events = {};
        try {
            events = (new FileIO()).getEvents(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }
}