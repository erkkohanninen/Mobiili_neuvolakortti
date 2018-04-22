package mobiili.neuvolakortti;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HeightFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        HeightFragmentChart heightFragmentChart = new HeightFragmentChart();
        fragmentTransaction.add(R.id.fragment_frame_height,heightFragmentChart);

        fragmentTransaction.commit();

        return inflater.inflate(R.layout.fragment_height, container, false);
    }
}
