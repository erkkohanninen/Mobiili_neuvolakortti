package mobiili.neuvolakortti;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class HeightFragmentList extends Fragment {

    private int childId;
    private String childName;
    private List<Child> lista;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_height_list, container, false);

        Bundle extras = getArguments();
        if (extras != null) {
            childId = extras.getInt("ID");
            childName = extras.getString("NAME");
        }

        getData();
        TextView tv = v.findViewById(R.id.textViewHeightList);
        tv.setText(""+lista);
        return v;
    }

    public void getData(){
        DbAdapter db = new DbAdapter(getActivity());
        db.open();
        lista = db.getHeights(childId);
        db.close();
    }
}
