package com.example.myadminappbatchone.pickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myadminappbatchone.utils.Constants;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerDialogeFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar calendar=Calendar.getInstance(Locale.getDefault());
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),this, year,month,dayOfMonth);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        final SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar=Calendar.getInstance();
        calendar.set(year,month,day);
        final String selectedDate=sdf.format(calendar.getTime());
        final Bundle bundle = new Bundle();
        bundle.putString(Constants.DATE_KEY, selectedDate);
        bundle.putInt(Constants.YEAR, year);
        bundle.putInt(Constants.MONTH, month);
        bundle.putInt(Constants.DAY, day);
        getParentFragmentManager()
                .setFragmentResult(Constants.REQUEST_KEY, bundle);
        //Log.e("Date",selectedDate);

    }
}
