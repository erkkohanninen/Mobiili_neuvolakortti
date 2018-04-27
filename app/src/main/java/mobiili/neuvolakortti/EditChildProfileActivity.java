package mobiili.neuvolakortti;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.UUID;

public class EditChildProfileActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText etName;
    private EditText etWeight;
    private EditText etHeight;
    private EditText etHead;
    private Button dateButton;
    private ImageView imageView;
    private int id;
    private String name = "";
    private String dateob = "";
    private String weight = "";
    private String height = "";
    private String head ="";
    private String newName ="";
    private String newDateToDB = null;
    private String childPhoto ="";
    private float newWeight;
    private float newHeight;
    private float newHead;
    private ActionBar actionBar;
    private DbAdapter db = new DbAdapter(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_child_profile);

        // add close button to actionbar
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_24dp);

        db.open();

        etName = (EditText) findViewById(R.id.et_ec_name);
        dateButton = (Button) findViewById(R.id.date_button);
        etWeight = (EditText) findViewById(R.id.et_ec_weight);
        etHeight = (EditText) findViewById(R.id.et_ec_height);
        etHead = (EditText) findViewById(R.id.et_ec_head);
        imageView = findViewById(R.id.photo);

        Intent intent = getIntent();
        id = getIntent().getExtras().getInt("ID");

        name = db.getCurrentChild(id, 1);
        dateob = db.getCurrentChild(id, 2);
        weight = db.getCurrentChildData(id, 2);
        height = db.getCurrentChildData(id, 3);
        head = db.getCurrentChildData(id, 4);
        childPhoto = db.getCurrentChild(id, 3);

        etName.setText(name);
        dateButton.setText(dateob);
        etWeight.setText(weight);
        etHeight.setText(height);
        etHead.setText(head);

        // add profile name to actionbar
        actionBar.setTitle("Muokkaa profiilia - " + name);

        if (childPhoto.equals("default")){
            imageView.setImageResource(R.drawable.ic_face_24dp);
        }
        else {
            imageView.setImageURI(getChildPhotoUri(childPhoto));
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (childPhoto.equals("default")) {
                    childPhoto = UUID.randomUUID().toString();
                }
                takePicture(getChildPhotoUri(childPhoto));
            }
        });
    }


    // Returns to previous page without saving child info to database
    void cancelEditChild(View view){
        onBackPressed();
        finish();
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("ID", id);
        setResult(RESULT_CANCELED, intent);
        finish();
        return;
    }

    // save new info to database and go back to profile

    void saveEditChild(View view){

        newName = etName.getText().toString();
        weight = etWeight.getText().toString();
        height = etHeight.getText().toString();
        head = etHead.getText().toString();

        String[] strings = {newName, weight, height, head};

        //Check that user has filled all information
        if (! areSet(strings)){
            Toast.makeText(this, "Täytä kaikki kohdat", Toast.LENGTH_LONG).show();
        }
        else {
            db = new DbAdapter(this);
            db.open();

            // update current child name and date of birth
            // if date of birth not changed, get original date
            if(newDateToDB != null) {
                db.updateChild(id, newName, newDateToDB, childPhoto);
            }
            else {
                newDateToDB = db.getCurrentChild(id, 2);
                db.updateChild(id, newName, newDateToDB, childPhoto);
            }
            // update current child's birth measures
            newWeight = Float.valueOf(weight);
            newHeight = Float.valueOf(height);
            newHead = Float.valueOf(head);
            db.updateChildMeasures(id, newDateToDB, newWeight, newHeight, newHead);

            Toast.makeText(this, "Uudet tiedot tallennettu", Toast.LENGTH_LONG).show();

            db.close();

            // go back to previous activity
            Intent intent = new Intent();
            intent.putExtra("ID", id);
            setResult(RESULT_OK, intent);
            finish();
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
        dateButton.setText(date);

        newDateToDB = strYear + "-" + strMonth + "-" + strDay;
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

    //Take picture with fab
    public void takePhoto(View view) {
        if (childPhoto.equals("default")) {
            childPhoto = UUID.randomUUID().toString();
        }
        takePicture(getChildPhotoUri(childPhoto));
    }


    //Profile picture
    public void takePicture(Uri outputUri) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                .setOutputCompressQuality(50)
                .setOutputUri(outputUri)
                .start(this);
    }

    public Uri getChildPhotoUri(String photo) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File file = new File(directory, photo + ".jpg");
        Uri outputUri = Uri.fromFile(file);

        Log.d("TAG", outputUri.toString());
        return outputUri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imageView.setImageURI(null);
                imageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    // when actionbar close button is pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
