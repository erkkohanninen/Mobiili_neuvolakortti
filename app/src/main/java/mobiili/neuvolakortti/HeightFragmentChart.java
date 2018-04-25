package mobiili.neuvolakortti;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class HeightFragmentChart extends Fragment {

    private int childId;
    private List<Child> lista;
    float count;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_height_chart,container,false);

        Bundle extras = getArguments();
        if (extras != null) {
            childId = extras.getInt("ID");
        }

        Log.d("Erkko","childId: " +childId);

        LineChart chart = (LineChart) v.findViewById(R.id.height_chart);
        getData();


        ArrayList<Entry> yEntries = new ArrayList<>();
        final ArrayList<String> xEntries = new ArrayList<>();
        count = 0;

        for(Child child : lista){
            yEntries.add(new Entry(count, child.getHeight()));
            count = count +1;
        }

        for (Child child : lista){
            xEntries.add(child.getDateMeasured());
        }


        Log.d("Erkko","xEntries: " +xEntries);
        Log.d("Erkko","yEntries: " +yEntries);

        final XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(-90);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String val = null;
                try {
                    val = xEntries.get((int) value);
                } catch (IndexOutOfBoundsException e) {
                    //xAxis.setGranularityEnabled(false);
                    return "";

                }

                return val;
            }
        });

        LineDataSet dataSet = new LineDataSet(yEntries, "Pituus");
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.getDescription().setEnabled(false);

        chart.invalidate();

        return v;
    }

    public void getData(){
        DbAdapter db = new DbAdapter(getActivity());
        db.open();
        lista = db.getHeights(childId);
        db.close();
    }

}
