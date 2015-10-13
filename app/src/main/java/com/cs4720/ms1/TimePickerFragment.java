package com.cs4720.ms1;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nathan on 9/23/2015.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public timePickable tp;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            tp = (timePickable) activity;
        } catch (ClassCastException e) {
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(0, 0, 0, hourOfDay, minute);
        if (getTag().equals("start")) {
            tp.setStartTime(c);
        }
        else {
            tp.setEndTime(c);
        }
    }

    public interface timePickable{
        void setStartTime(Calendar startTime);
        void setEndTime(Calendar endTime);
    }
}