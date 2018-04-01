package mobiili.neuvolakortti;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class AddChildActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText etChildname;
    private EditText etWeight;
    private EditText etHeight;
    private EditText etHead;
    private String dateToDatabase;
    private DbAdapter db;

    //lisää tähän muut jutut

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        etChildname = (EditText)findViewById(R.id.etChildName);
        etWeight = (EditText)findViewById(R.id.et_ac_weight);
        etHeight = (EditText)findViewById(R.id.et_ac_height);
        etHead = (EditText)findViewById(R.id.et_ac_head);
        db = new DbAdapter(this);
    }

    // Returns to MainActivity without saving child info to database

    void cancelAddChild(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // save child´s info to database and return to MainActivity

    void saveAddChild(View view){

        db.open();
        db.addChild(new Child(etChildname.getText().toString(), dateToDatabase, Float.valueOf(etWeight.getText().toString()),
                Float.valueOf(etHeight.getText().toString()), Float.valueOf(etHead.getText().toString())));

        // Reading all children DEBUGGAUSTA VARTEN
        Log.d("Reading: ", "Reading all children..");
        List<Child> children = db.getAllChildren();

        for (Child child : children) {
            String log = "Id: " + child.getId() + " ,Name: " + child.getName() + " ,Date of birth: " + child.getDateOfBirth()
                    + " ,Weight:" + child.getWeight() + " ,Height:" + child.getHeight() + " ,Head:" + child.getHead();
            // Writing shops  to log
            Log.d("Children: : ", log);
        }
        db.close();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        //Ilmoitus toast tietojen lisäämisestä?
    }


    // Sets the chosen date as text "DD.MM.YYYY" to button
    // and converts date to "YYYY-MM-DD" -format to save to database
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {

        month = month + 1;
        String strYear = String.valueOf(year);
        String strMonth = String.valueOf(month);
        String strDay = String.valueOf(day);


        if(month < 9) {
            strMonth = "0" + strMonth;

        }

        if(day < 10){
            strDay = "0" + strDay;
        }

        String date = Integer.toString(day) + "." + Integer.toString(month)
                        + "." + Integer.toString(year);
        Button dof_button = (Button)findViewById(R.id.btn_date_of_birth);
        dof_button.setText(date);

        dateToDatabase = strYear + "-" + strMonth + "-" + strDay;
    }

    //called when date of birth button is pressed
    public void datePicker(View view){
        DialogFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), "datePicker");

    }

}
