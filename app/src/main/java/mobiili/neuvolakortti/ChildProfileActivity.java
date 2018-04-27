package mobiili.neuvolakortti;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ChildProfileActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView ageTextView;
    private ImageButton editButton;
    private ImageView imageView;
    private String childName;
    private String childAge;
    private String childPhoto;
    private Integer id;
    private String dateToDatabase = "";
    private DbAdapter db = new DbAdapter(this);
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile);

        // add back button to actionbar
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        id = getIntent().getExtras().getInt("ID");
        editButton = (ImageButton) findViewById(R.id.edit_profile_button);
        nameTextView = (TextView) findViewById(R.id.tv_profile_name);
        ageTextView = (TextView) findViewById(R.id.tv_profile_age);
        imageView = findViewById(R.id.photo);

        onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    // when actionbar back button pressed
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void editProfile(View view) {
        Intent intent = new Intent(this, EditChildProfileActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

    public void goToVaccination(View view){
        Intent intent = new Intent(this, VaccinationActivity.class);
        intent.putExtra("NAME", childName);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

    public void goToGrowth(View view){
        Intent intent = new Intent(this, GrowthActivity.class);
        intent.putExtra("NAME", childName);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

    public void goToDevelopment(View view){
        Intent intent = new Intent(this, DevelopmentActivity.class);
        intent.putExtra("NAME", childName);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

    // Calculate age from todays date and birthdate
    public static String calcAge(String birthDate) {
        SimpleDateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfBirth = null;
        Date dateOfToday = new Date();

        try {
            dateOfBirth = fullFormat.parse(birthDate);

        } catch (Exception e) {
            e.printStackTrace();
        }

        long ageMs = dateOfToday.getTime() - dateOfBirth.getTime();

        long ageYear = ageMs/31556952000L;
        long ageMonth = (ageMs % 31556952000L) / 2629746000L;
        long ageDays = ((ageMs % 31556952000L) % 2629746000L) / 86400000;

        Log.d("Year", "calcAge: " + ageYear +"years, " + ageMonth +"months, " +ageDays + "days");

        String age = ageYear +"v, " + ageMonth +"kk, " +ageDays + "pv";

        return age;
    }

    // getting data from previous activity:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            id = data.getIntExtra("ID", id);
        }

    }

    // for refreshing the activity
    @Override
    public void onResume() {
        super.onResume();
        db.open();

        childName = db.getCurrentChild(id, 1);
        childAge = db.getCurrentChild(id, 2);
        childPhoto = db.getCurrentChild(id, 3);

        //change the text in actionbar
        actionBar = getSupportActionBar();
        actionBar.setTitle("Profiili - " + childName);

        nameTextView.setText(childName);
        if (childAge != null) {
            // calculate child's age by date of birth
            ageTextView.setText(calcAge(childAge));
        }
        else {
            ageTextView.setText("notfound");
        }

        db.close();

        if (childPhoto.equals("default")){
            imageView.setImageResource(R.drawable.ic_face_24dp);
        }
        else {
            imageView.setImageURI(null);
            imageView.setImageURI(getChildPhotoUri(childPhoto, getApplicationContext()));
        }
    }

    public Uri getChildPhotoUri(String photo, Context context) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File file = new File(directory, photo + ".jpg");
        Uri outputUri = Uri.fromFile(file);
        Log.d("TAG", outputUri.toString());

        return outputUri;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_favorite:
                Intent intent = new Intent(this, EditChildProfileActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}

