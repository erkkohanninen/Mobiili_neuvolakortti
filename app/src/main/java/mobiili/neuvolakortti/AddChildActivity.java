package mobiili.neuvolakortti;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class AddChildActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText etChildname;
    private EditText etWeight;
    private EditText etHeight;
    private EditText etHead;
    private ImageView imageView;
    private String dateToDatabase = "";
    private String childName = "";
    private String weight = "";
    private String height = "";
    private String head ="";
    private String photo = "default";
    private DbAdapter db = new DbAdapter(this);
    private android.support.v7.app.ActionBar actionBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        // add close button to actionbar
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_24dp);

        etChildname = (EditText)findViewById(R.id.etChildName);
        etWeight = (EditText)findViewById(R.id.et_ac_weight);
        etHeight = (EditText)findViewById(R.id.et_ac_height);
        etHead = (EditText)findViewById(R.id.et_ac_head);
        imageView = findViewById(R.id.photo);

        //change the text in actionbar
        actionBar.setTitle("Lisää lapsen tiedot");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto(view);
            }
            });
    }

    // Returns to MainActivity without saving child info to database

    public void cancelAddChild(View view){
        finish();
    }

    // save child´s info to database and return to MainActivity

    public void saveAddChild(View view){
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
            db.addChild(new Child(childName, dateToDatabase, photo, Float.valueOf(weight),
                    Float.valueOf(height), Float.valueOf(head)));
            Toast.makeText(this, "Lapsen tiedot lisätty", Toast.LENGTH_LONG).show();
            db.close();

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

    // Add picture fab
    public void takePhoto(View view) {
        if (photo.equals("default")) {
            photo = UUID.randomUUID().toString();
        }
        takePicture(getChildPhotoUri(photo));
    }

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
                imageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    // when actionbar close button is clicked
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
