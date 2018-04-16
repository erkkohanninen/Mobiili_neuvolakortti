package mobiili.neuvolakortti;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
}
