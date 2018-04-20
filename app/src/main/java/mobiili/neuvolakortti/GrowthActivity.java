package mobiili.neuvolakortti;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.List;

public class GrowthActivity extends AppCompatActivity {
    private String childWeight;
    private String childHeight;
    private String childHead;
    private String dateToDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growth);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        HeightFragment heightFragment = new HeightFragment();
        fragmentTransaction.add(R.id.fragment_frame, heightFragment);
        fragmentTransaction.commit();
    }

    public void changeFragment (View view){

        Fragment newFragment;

        if (view == findViewById(R.id.buttonToHeight)){

            newFragment = new HeightFragment();

        }   else if (view == findViewById(R.id.buttonToWeight)){

            newFragment = new WeightFragment();

        }   else if (view == findViewById(R.id.buttonToHead)){

            newFragment = new HeadFragment();

        } else {

            newFragment = new HeightFragment();
        }

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_frame, newFragment);
        fragmentTransaction.commit();
    }

    public void addGrowth(View view){

        // Dialog
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(GrowthActivity.this);
        View mview = getLayoutInflater().inflate(R.layout.dialog_add_growth, null);
        mDialogBuilder.setTitle(R.string.add_growth);
        final DatePicker mPicker = (DatePicker) mview.findViewById(R.id.datePicker_vaccine);
        final EditText etWeight = (EditText) mview.findViewById(R.id.dialog_add_weight);
        final EditText etHeight = (EditText) mview.findViewById(R.id.dialog_add_height);
        final EditText etHead = (EditText) mview.findViewById(R.id.dialog_add_head);

        // Actions when user presses positive button
        mDialogBuilder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                childWeight = etWeight.getText().toString();
                childHeight = etHeight.getText().toString();
                childHead = etHead.getText().toString();

                if( TextUtils.isEmpty(childWeight)){
                    childWeight = "0.0";
                }
                if (TextUtils.isEmpty(childHeight)){
                    childHeight = "0.0";
                }
                if (TextUtils.isEmpty(childHead)){
                    childHead = "0.0";
                }

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

                dateToDatabase = year + "-" + month + "-" + day;

                DbAdapter db = new DbAdapter(GrowthActivity.this);
                db.open();
                //LISÄÄ TÄHÄN OIKEA ID!!
                db.addMeasures(new Child(1, Float.valueOf(childWeight),
                        Float.valueOf(childHeight), Float.valueOf(childHead), dateToDatabase));
                db.close();
                //Log.d("MITAT:", childWeight + " " + childHeight + " " + childHead + " " + dateToDatabase);

            }
        });

        // Action when user presses negative button
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

}
