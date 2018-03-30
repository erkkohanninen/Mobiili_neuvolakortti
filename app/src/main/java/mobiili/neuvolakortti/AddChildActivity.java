package mobiili.neuvolakortti;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Calendar;

public class AddChildActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText etChildname;
    //lisää tähän muut jutut

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        etChildname = (EditText)findViewById(R.id.etChildName);
        //lisää muut!!
    }

    void cancelAddChild(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    void saveAddChild(View view){

        //nimi editTextistä, syntymäaika kalenterista, pituus, paino, päänympärys: tallenna tietokantaan
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        //Ilmoitus toast tietojen lisäämisestä?
    }


    // Sets the chosen date as text to button
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        month += 1;
        String pvm = Integer.toString(day) + "." + Integer.toString(month)
                        + "." + Integer.toString(year);
        Button dof_button = (Button)findViewById(R.id.btn_date_of_birth);
        dof_button.setText(pvm);
    }

    //called when date of birth button is pressed
    public void datePicker(View view){
        DialogFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), "datePicker");

    }

}
