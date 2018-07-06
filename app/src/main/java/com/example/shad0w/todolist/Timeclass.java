package com.example.shad0w.todolist;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class Timeclass extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c=Calendar.getInstance();
        int min=c.get(Calendar.MINUTE);
        int Hour=c.get(Calendar.HOUR_OF_DAY);
        return new TimePickerDialog(getActivity(),(TimePickerDialog.OnTimeSetListener) getActivity(),Hour,min, android.text.format.DateFormat.is24HourFormat(getActivity()));
    }
}
