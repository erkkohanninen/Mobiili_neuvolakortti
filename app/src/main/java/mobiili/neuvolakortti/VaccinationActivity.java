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

    private List<Vaccine> vaccineList;
    private RecyclerView recyclerView;
    private VaccinationAdapter mAdapter;
    private ArrayList<String> vaccines;
    private String childName;
    private int childId;
    private DbAdapter dbb = new DbAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            childName = extras.getString("NAME");
            childId = extras.getInt("ID");
        }

        // Populate recyclerview with child´s vaccinations
        getAllVaccinations();
        recyclerView = (RecyclerView) findViewById(R.id.rv_vaccination);
        mAdapter = new VaccinationAdapter(vaccineList, dbb);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
    }

    // Dialog to add new vaccination

    public void addVaccination(View view){

        loadSpinnerData();
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(VaccinationActivity.this);
        View mview = getLayoutInflater().inflate(R.layout.dialog_add_vaccination, null);
        mDialogBuilder.setTitle(R.string.add_vaccination);
        final Spinner mSpinner = (Spinner) mview.findViewById(R.id.spinner_vaccine);
        final DatePicker mPicker = (DatePicker) mview.findViewById(R.id.datePicker_vaccine);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(VaccinationActivity.this,
                android.R.layout.simple_spinner_item, vaccines);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);


        mDialogBuilder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
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
                db.addVaccination(childId, vaccineName, dateGiven);

                //Clear current items from vaccineList and refresh it with new data
                vaccineList.clear();
                vaccineList.addAll(db.getAllVaccinations(childId));
                mAdapter.notifyDataSetChanged();
                db.close();
            }
        });

        mDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        mDialogBuilder.setView(mview);
        AlertDialog dialog = mDialogBuilder.create();
        dialog.show();


    }

    // Get all vaccine names to a list

    public void loadSpinnerData(){
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

    // Get all child´ vaccinations to vaccineList

    public void getAllVaccinations(){
        DbAdapter db = new DbAdapter(this);
        db.open();
        vaccineList = db.getAllVaccinations(childId);
        db.close();
    }

}
