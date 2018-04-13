package mobiili.neuvolakortti;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class VaccinationActivity extends AppCompatActivity {

    private List<Vaccine> vaccineList = new ArrayList<>();
    private RecyclerView recyclerView;
    private VaccinationAdapter mAdapter;
    private DbAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination);
        recyclerView = (RecyclerView) findViewById(R.id.rv_vaccination);

        mAdapter = new VaccinationAdapter(vaccineList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        debugvaccineList();
    }

    public void addVaccination(View view){

    }

    public void debugvaccineList(){
        Vaccine vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
        vaccine = new Vaccine("hoplaa", "1.1.2017");
        vaccineList.add(vaccine);
    }
}
