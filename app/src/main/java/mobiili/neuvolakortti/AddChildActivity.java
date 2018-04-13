package mobiili.neuvolakortti;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import java.util.List;

public class AddChildActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText etChildname;
    private EditText etWeight;
    private EditText etHeight;
    private EditText etHead;
    private String dateToDatabase = "";
    private String childName = "";
    private String weight = "";
    private String height = "";
    private String head ="";
    private DbAdapter db;



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
        childName = etChildname.getText().toString();
        weight = etWeight.getText().toString();
        height = etHeight.getText().toString();
        head = etHead.getText().toString();
        String[] strings = {childName, dateToDatabase, weight, height, head};

        //Check that user has filled all information
       if (! areSet(strings)){
           Toast.makeText(this, "Täytä kaikki kohdat!", Toast.LENGTH_LONG).show();
       }
        //Check if child´s info already exists
       else if(!db.checkIfExists(childName)){
           Toast.makeText(this, "Lapsen tiedot on jo lisätty", Toast.LENGTH_LONG).show();
       }
       else {
            db.addChild(new Child(childName, dateToDatabase, Float.valueOf(weight),
                    Float.valueOf(height), Float.valueOf(head)));
            Toast.makeText(this, "Lapsen tiedot lisätty", Toast.LENGTH_LONG).show();

             /**
             * DEBUGGAUS VARTEN LAPSIOLIOIDEN JA KEHITYSASKEL-LISTAN LÄPIKÄYNTI
             */
            Log.d("Reading: ", "Reading all children..");
            List<Child> children = db.getAllChildren();

            for (Child child : children) {
                String log = "Id: " + child.getId() + " ,Name: " + child.getName() + " ,Date of birth: " + child.getDateOfBirth();
                // Writing shops  to log
                Log.d("Children: : ", log);
            }


            List<String> developments = db.getAllDevelopments();

            for (String development : developments){
                String log = "Development: " + development;
                Log.d("Developments: ", log);
            }

            db.close();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            }

    }


    // Sets the chosen date as text "DD.MM.YYYY" to button
    // and converts date to "YYYY-MM-DD" -format to save to database*/
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

    public boolean areSet(String... strings)
    {
        for (String s: strings)
            if(TextUtils.isEmpty(s)){
            return false;
        }
        return true;
    }

}
