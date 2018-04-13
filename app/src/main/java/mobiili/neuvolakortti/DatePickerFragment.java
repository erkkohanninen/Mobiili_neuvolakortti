package mobiili.neuvolakortti;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by tanja on 30/03/2018.
 */

/**
 * Create a DatePickerFragment class that extends DialogFragment.
 * Define the onCreateDialog() method to return an instance of DatePickerDialog
 */
public class DatePickerFragment extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog,
                (DatePickerDialog.OnDateSetListener)
                        getActivity(), year, month, day);
    }

}


