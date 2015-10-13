package com.cs4720.ms1;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewEvent extends Activity implements TimePickerFragment.timePickable, DatePickerFragment.datePickable {

    private EventTracker newEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        newEvent = new EventTracker();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_event, menu);
        return true;
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
        TextView tv = (TextView)findViewById(R.id.startTime);
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("hh:mm a");
        String time = sdf.format(startTime.getTime());
        tv.setText(time);
    }

    @Override
    public void setEndTime(Calendar endTime) {
        newEvent.setEndTime(endTime);
        TextView tv = (TextView)findViewById(R.id.endTime);
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("hh:mm a");
        String time = sdf.format(endTime.getTime());
        tv.setText(time);
    }


    @Override
    public void setStartDate(Calendar startDate) {
        newEvent.setStartDate(startDate);
        TextView tv = (TextView)findViewById(R.id.startDate);
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("MM/dd/yyyy");
        String time = sdf.format(startDate.getTime());
        tv.setText(time);
    }

    @Override
    public void setEndDate(Calendar endDate) {
        newEvent.setEndDate(endDate);
        TextView tv = (TextView)findViewById(R.id.endDate);
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("MM/dd/yyyy");
        String time = sdf.format(endDate.getTime());
        tv.setText(time);
    }

    public void saveEvent(View view) throws IOException {
        if(newEvent.getEndTime() - newEvent.getStartTime() < 0) {
            TextView tv = (TextView)findViewById(R.id.errorField);
            tv.setText("End time must be after start time");
        }
        EditText eventName = (EditText)findViewById(R.id.eventName);
        newEvent.setName(eventName.getText().toString());
        newEvent.save(getApplicationContext());
    }
}