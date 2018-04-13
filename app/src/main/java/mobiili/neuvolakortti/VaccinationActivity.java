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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            childName = extras.getString("NAME");
        }


        recyclerView = (RecyclerView) findViewById(R.id.rv_vaccination);
        mAdapter = new VaccinationAdapter(vaccineList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        debugvaccineList();
    }

    public void addVaccination(View view){

        loadSpinnerData();
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(VaccinationActivity.this);
        View mview = getLayoutInflater().inflate(R.layout.dialog_add_vaccination, null);
        mDialogBuilder.setTitle("Lisää rokotus");
        Spinner mSpinner = (Spinner) mview.findViewById(R.id.spinner_vaccine);
        DatePicker mPicker = (DatePicker) mview.findViewById(R.id.datePicker_vaccine);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(VaccinationActivity.this,
                android.R.layout.simple_spinner_item, vaccines);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);


        mDialogBuilder.setPositiveButton("Lisää", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Tähän tallennus tietokantaan
                dialogInterface.dismiss();
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


    public void debugvaccineList(){
        Vaccine vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
    }
}
