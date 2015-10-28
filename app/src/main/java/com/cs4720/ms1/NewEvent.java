package com.cs4720.ms1;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewEvent extends Activity implements TimePickerFragment.timePickable, DatePickerFragment.datePickable {

    private EventTracker newEvent;
    public boolean startTimeSet;
    public boolean endTimeSet;
    public boolean startDateSet;
    public boolean endDateSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        newEvent = new EventTracker();
        if(savedInstanceState != null)
        {
            ((TextView)findViewById(R.id.setStartTime)).setText(savedInstanceState.getString("startTime"));
            ((TextView)findViewById(R.id.setEndTime)).setText(savedInstanceState.getString("endTime"));
            ((TextView)findViewById(R.id.setStartDate)).setText(savedInstanceState.getString("startDate"));
            ((TextView)findViewById(R.id.setEndDate)).setText(savedInstanceState.getString("endDate"));
            ((EditText)findViewById(R.id.eventName)).setText(savedInstanceState.getString("eventName"));
            startTimeSet = savedInstanceState.getBoolean("startTimeSet");
            endTimeSet = savedInstanceState.getBoolean("endTimeSet");
            startDateSet = savedInstanceState.getBoolean("startDateSet");
            endDateSet = savedInstanceState.getBoolean("endDateSet");
            newEvent.setStartTimeInMillis(savedInstanceState.getLong("eStartTime"));
            newEvent.setEndTimeInMillis(savedInstanceState.getLong("eEndTime"));
        }
        else
        {
            startTimeSet = false;
            endTimeSet = false;
            startDateSet = false;
            endDateSet = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_event, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startTimePressed(View view){
        TimePickerFragment newFragment = new TimePickerFragment();
        FragmentManager f = getFragmentManager();
        newFragment.show(f, "start");
    }

    public void endTimePressed(View view){
        TimePickerFragment newFragment = new TimePickerFragment();
        FragmentManager f = getFragmentManager();
        newFragment.show(f, "end");
    }

    public void startDatePressed(View view){
        DatePickerFragment newFragment = new DatePickerFragment();
        FragmentManager f = getFragmentManager();
        newFragment.show(f, "start");
    }

    public void endDatePressed(View view){
        DatePickerFragment newFragment = new DatePickerFragment();
        FragmentManager f = getFragmentManager();
        newFragment.show(f, "end");
    }

    @Override
    public void setStartTime(Calendar startTime) {
        newEvent.setStartTime(startTime);
        Button b = (Button)findViewById(R.id.setStartTime);
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("hh:mm a");
        String time = sdf.format(startTime.getTime());
        b.setText(time);
        startTimeSet = true;
    }

    @Override
    public void setEndTime(Calendar endTime) {
        newEvent.setEndTime(endTime);
        Button b = (Button)findViewById(R.id.setEndTime);
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("hh:mm a");
        String time = sdf.format(endTime.getTime());
        b.setText(time);

        endTimeSet = true;
    }


    @Override
    public void setStartDate(Calendar startDate) {
        newEvent.setStartDate(startDate);
        Button b = (Button)findViewById(R.id.setStartDate);
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("MM/dd/yyyy");
        String time = sdf.format(startDate.getTime());
        b.setText(time);

        startDateSet = true;
    }

    @Override
    public void setEndDate(Calendar endDate) {
        newEvent.setEndDate(endDate);
        Button b = (Button)findViewById(R.id.setEndDate);
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("MM/dd/yyyy");
        String time = sdf.format(endDate.getTime());
        b.setText(time);

        endDateSet = true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        String startTime = ((TextView)findViewById(R.id.setStartTime)).getText().toString();
        String endTime = ((TextView)findViewById(R.id.setEndTime)).getText().toString();
        String startDate = ((TextView)findViewById(R.id.setStartDate)).getText().toString();
        String endDate = ((TextView)findViewById(R.id.setEndDate)).getText().toString();
        String eventName = ((EditText)findViewById(R.id.eventName)).getText().toString();
        savedInstanceState.putString("eventName", eventName);
        savedInstanceState.putString("startTime",startTime );
        savedInstanceState.putString("endTime", endTime);
        savedInstanceState.putString("startDate", startDate);
        savedInstanceState.putString("endDate", endDate);
        savedInstanceState.putBoolean("startTimeSet", startTimeSet);
        savedInstanceState.putBoolean("startTimeSet",endTimeSet);
        savedInstanceState.putBoolean("startTimeSet",startDateSet);
        savedInstanceState.putBoolean("startTimeSet", endDateSet);
        savedInstanceState.putLong("eStartTime",newEvent.getStartTimeInMillis());
        savedInstanceState.putLong("eEndTime",newEvent.getEndTimeInMillis());
    }

    public void saveEvent(View view) throws IOException {
        String eventName = ((EditText)findViewById(R.id.eventName)).getText().toString();

        if(eventName.equals("")){
            TextView tv = (TextView)findViewById(R.id.errorField);
            tv.setText("You must specify an Event name.");
        }
        else if(!startTimeSet){
            TextView tv = (TextView)findViewById(R.id.errorField);
            tv.setText("You must specify a start time.");
        }
        else if(!endTimeSet){
            TextView tv = (TextView)findViewById(R.id.errorField);
            tv.setText("You must specify an end time.");
        }
        else if(!startDateSet){
            TextView tv = (TextView)findViewById(R.id.errorField);
            tv.setText("You must specify a start date.");
        }
        else if(!endDateSet){
            TextView tv = (TextView)findViewById(R.id.errorField);
            tv.setText("You must specify an end date.");
        }
        else if(newEvent.getEndTime() - newEvent.getStartTime() < 0) {
            TextView tv = (TextView)findViewById(R.id.errorField);
            tv.setText("End time must be after start time.");
        }
        else if((new FileIO()).containsEventName(eventName,getApplicationContext())){
            TextView tv = (TextView)findViewById(R.id.errorField);
            tv.setText("Event with that name already exists.");
        }
        else {
            newEvent.setName(eventName);
            newEvent.save(getApplicationContext());
            Toast.makeText(this, "Event Creted", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
