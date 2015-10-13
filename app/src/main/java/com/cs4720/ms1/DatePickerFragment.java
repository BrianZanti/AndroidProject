package com.cs4720.ms1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    datePickable dp;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            dp = (datePickable) activity;
        } catch (ClassCastException e) {
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth, 0, 0);
        if (getTag().equals("start")) {
            dp.setStartDate(c);
        }
        else {
            dp.setEndDate(c);
        }
    }

    public interface datePickable{
        void setStartDate(Calendar startTime);
        void setEndDate(Calendar endTime);
    }
}
