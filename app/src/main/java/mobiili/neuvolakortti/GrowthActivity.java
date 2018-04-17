package mobiili.neuvolakortti;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class GrowthActivity extends AppCompatActivity {

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

                //Tähän tekstit editteksteistä ja tallennus tietokantaan lapsen id:n perusteella
                //Tarkistukset, ettei syötetty tyhjää?
                dialogInterface.dismiss();

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
