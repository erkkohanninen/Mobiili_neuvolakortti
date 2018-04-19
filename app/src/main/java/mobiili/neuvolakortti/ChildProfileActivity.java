package mobiili.neuvolakortti;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;


public class ChildProfileActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView ageTextView;
    private Button editButton;
    public Child currentChild;
    private String childName;
    private String childAge;
    private int id;
    private String dateToDatabase = "";
    Dialog dialogEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile);

        nameTextView = (TextView) findViewById(R.id.tv_profile_name);
        ageTextView = (TextView) findViewById(R.id.tv_profile_age);

        id = getIntent().getIntExtra("ID", 0);
        childName = getIntent().getStringExtra("NAME");
        childAge = getIntent().getStringExtra("AGE");

        if (childName != null && childAge != null) {
            nameTextView.setText(childName);
            ageTextView.setText(childAge);
        }
    }

    public void goToVaccination(View view){
        Intent intent = new Intent(this, VaccinationActivity.class);
        intent.putExtra("NAME", childName);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

    public void goToGrowth(View view){
        Intent intent = new Intent(this, GrowthActivity.class);
        startActivity(intent);
    }

    public void goToDevelopment(View view){
        Intent intent = new Intent(this, DevelopmentActivity.class);
        intent.putExtra("NAME", childName);
        intent.putExtra("ID", id);
        startActivity(intent);
    }


}

