package mobiili.neuvolakortti;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class WeightFragmentList extends Fragment {

    private int childId;
    private List<Child> lista;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weight_list, container, false);

        Bundle extras = getArguments();
        if (extras != null) {
            childId = extras.getInt("ID");
        }

        getData();
        TextView tv = v.findViewById(R.id.textViewWeightList);
        for (Child child : lista) {
            String entry = child.getDateMeasured() + "   " + Float.toString(child.getWeight()) + " kg" + "\n";
            tv.append(entry);
        }
        return v;
    }

    public void getData() {
        DbAdapter db = new DbAdapter(getActivity());
        db.open();
        lista = db.getWeights(childId);
        db.close();
    }
}
