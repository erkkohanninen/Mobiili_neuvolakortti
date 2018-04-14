package mobiili.neuvolakortti;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VaccinationActivity extends AppCompatActivity{

    private List<Vaccine> vaccineList = new ArrayList<>();
    private RecyclerView recyclerView;
    private VaccinationAdapter mAdapter;
    private ArrayList<String> vaccines;
    private String childName;
    private int childId;
    private List<Vaccine> vaccinations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            childName = extras.getString("NAME");
            childId = extras.getInt("ID");
        }

        getAllVaccinations();
        recyclerView = (RecyclerView) findViewById(R.id.rv_vaccination);
        mAdapter = new VaccinationAdapter(vaccineList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        //debugvaccineList();
        //getAllVaccinations();
    }

    public void addVaccination(View view){

        loadSpinnerData();
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(VaccinationActivity.this);
        View mview = getLayoutInflater().inflate(R.layout.dialog_add_vaccination, null);
        mDialogBuilder.setTitle("Lisää rokotus");
        final Spinner mSpinner = (Spinner) mview.findViewById(R.id.spinner_vaccine);
        final DatePicker mPicker = (DatePicker) mview.findViewById(R.id.datePicker_vaccine);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(VaccinationActivity.this,
                android.R.layout.simple_spinner_item, vaccines);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);


        mDialogBuilder.setPositiveButton("Lisää", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Vaccine name from Spinner
                String vaccineName = (String) mSpinner.getSelectedItem();

                // date from datepicker
                String year = String.valueOf(mPicker.getYear());
                String month = String.valueOf(mPicker.getMonth() + 1 );
                String day = String.valueOf(mPicker.getDayOfMonth());

                if(mPicker.getMonth() < 9) {
                    month = "0" + month;

                }

                if(mPicker.getDayOfMonth() < 10){
                    day = "0" + day;
                }

                String dateGiven = year + "-" + month + "-" + day;
                DbAdapter db = new DbAdapter(VaccinationActivity.this);
                db.open();
                db.addVaccination(childName, vaccineName, dateGiven);
                db.close();
            }
        });

        mDialogBuilder.setNegativeButton("Peruuta", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        mDialogBuilder.setView(mview);
        AlertDialog dialog = mDialogBuilder.create();
        dialog.show();


    }

    public void loadSpinnerData(){

        //lisää tähän haku tietokannasta -> kaikki rokotusten nimet

        DbAdapter db = new DbAdapter(this);
        db.open();
        Cursor cursor = db.getAllVaccines();
        vaccines = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            do {
                vaccines.add(cursor.getString(0));
            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();

    }

    public void getAllVaccinations(){
        DbAdapter db = new DbAdapter(this);
        db.open();
        vaccineList = db.getAllVaccinations(childId);
        vaccinations = db.getAllVaccinations(childId);

        Log.d("Reading: ", "Reading all vaccinations..");

        for (Vaccine vaccine : vaccinations) {
            String log = "Name: " + vaccine.getName() + " ,Date: " + vaccine.getDate();
            // Writing shops  to log
            Log.d("Vaccinations: : ", log);
        }
        db.close();


    }


    public void debugvaccineList(){
        Vaccine vaccine = new Vaccine("MPR", "1.7.2014");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("Vesirokko", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("Rotavirus", "20.3.2016");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("PCV", "30.2.2015");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("DTaP-IPV-Hib", "15.7.2014");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("MPR", "26.4.2016");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("Influenssa", "29.10.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("Vesirokko", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("Rotavirus", "20.3.2016");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("PCV", "30.2.2015");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("DTaP-IPV-Hib", "15.7.2014");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("MPR", "26.4.2016");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("Influenssa", "29.10.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("Vesirokko", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("Rotavirus", "20.3.2016");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("PCV", "30.2.2015");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("DTaP-IPV-Hib", "15.7.2014");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("MPR", "26.4.2016");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("Influenssa", "29.10.2017");
        vaccineList.add(vaccine);

    }

}
