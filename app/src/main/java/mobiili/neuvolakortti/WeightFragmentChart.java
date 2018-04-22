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

public class WeightFragmentChart extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weight_chart,container,false);
        LineChart chart = (LineChart) v.findViewById(R.id.weight_chart);

        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(new Entry(1,8));
        entries.add(new Entry(2,7));

        LineDataSet dataSet = new LineDataSet(entries, "Paino");
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);

        chart.invalidate();

        return v;
    }
}
