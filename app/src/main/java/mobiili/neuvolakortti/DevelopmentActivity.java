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

public class DevelopmentActivity extends AppCompatActivity{

    private List<Development> developmentList;
    private RecyclerView recyclerView;
    private DevelopmentAdapter mAdapter;
    private ArrayList<String> developments;
    private String childName;
    private int childId;
    private DbAdapter dbb = new DbAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_development);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            childName = extras.getString("NAME");
            childId = extras.getInt("ID");
        }

        // Populate recyclerview with child´s developments
        getAllChildDevelopments();
        recyclerView = (RecyclerView) findViewById(R.id.rv_development);
        mAdapter = new DevelopmentAdapter(developmentList, dbb);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
    }

    // Dialog to add new development

    public void addDevelopment(View view){

        loadSpinnerData();
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(DevelopmentActivity.this);
        View mview = getLayoutInflater().inflate(R.layout.dialog_add_development, null);
        mDialogBuilder.setTitle(R.string.add_development);
        final Spinner mSpinner = (Spinner) mview.findViewById(R.id.spinner_development);
        final DatePicker mPicker = (DatePicker) mview.findViewById(R.id.datePicker_development);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(DevelopmentActivity.this,
                android.R.layout.simple_spinner_item, developments);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);


        mDialogBuilder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Development name from Spinner
                String developmentName = (String) mSpinner.getSelectedItem();

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

                String dateReached = year + "-" + month + "-" + day;
                DbAdapter db = new DbAdapter(DevelopmentActivity.this);
                db.open();
                db.addDevelopment(childId, developmentName, dateReached);

                //Clear current items from developmentList and refresh it with new data
                developmentList.clear();
                developmentList.addAll(db.getAllChildDevelopments(childId));
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

    // Get all development names to a list

    public void loadSpinnerData(){
        DbAdapter db = new DbAdapter(this);
        db.open();
        developments = db.getAllDevelopments();
        db.close();

    }

    // Get all child´ developments to develompentList

    public void getAllChildDevelopments(){
        DbAdapter db = new DbAdapter(this);
        db.open();
        developmentList = db.getAllChildDevelopments(childId);
        db.close();
    }

}
