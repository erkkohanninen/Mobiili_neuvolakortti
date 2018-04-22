package mobiili.neuvolakortti;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class HeightFragmentChart extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_height_chart,container,false);
        LineChart chart = (LineChart) v.findViewById(R.id.height_chart);

        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(new Entry(1,2));
        entries.add(new Entry(2,3));

        LineDataSet dataSet = new LineDataSet(entries, "Pituus");
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);

        chart.invalidate();

        return v;
    }
}
