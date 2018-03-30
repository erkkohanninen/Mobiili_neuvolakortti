package mobiili.neuvolakortti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddChildActivity extends AppCompatActivity {

    private EditText etChildname;
    //lisää tähän muut jutut

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        etChildname = (EditText)findViewById(R.id.etChildName);
        //lisää muut!!
    }

    void cancelAddChild(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    void saveChild(View view){

        //nimi editTextistä, syntymäaika kalenterista, pituus, paino: tallenna tietokantaan
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        //Ilmoitus toast tietojen lisäämisestä?
    }
}
