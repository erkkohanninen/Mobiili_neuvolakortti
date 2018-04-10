package mobiili.neuvolakortti;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
 * {@link AndroidFlavorAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a data source, which is a list of {@link AndroidFlavor} objects.
 * */
public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.MyViewHolder> {
    public List<Child> myValues;
    public ChildAdapter (List<Child> myValues){
        this.myValues= myValues;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_list_item, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.currentChild = myValues.get(position);
        holder.nameTextView.setText(holder.currentChild.getName());
        holder.ageTextView.setText(calcAge(holder.currentChild.getDateOfBirth()));
    }


    @Override
    public int getItemCount() {
        return myValues.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView ageTextView;
        public View view;
        public Child currentChild;

        public MyViewHolder(final View itemView) {
            super(itemView);
            view = itemView;
            //Click listener for profile
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String currentChildName = (currentChild.getName());
                    String currentChildAge = calcAge((currentChild.getDateOfBirth()));
                    int currentChildId = (currentChild.getId());
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ChildProfileActivity.class);
                    // sending current child's name, age and id to ChildProfileActivity
                    intent.putExtra("NAME", currentChildName);
                    intent.putExtra("AGE", currentChildAge);
                    intent.putExtra("ID", currentChildId);
                    context.startActivity(intent);
                }
            });
            nameTextView = itemView.findViewById(R.id.tv_child_name);
            ageTextView = itemView.findViewById(R.id.tv_child_age);
        }
    }


    // Calculate age from todays date and birthdate
    public static String calcAge(String birthDate) {
        SimpleDateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfBirth = null;
        Date dateOfToday = new Date();

        try {
            dateOfBirth = fullFormat.parse(birthDate);

        } catch (Exception e) {
            e.printStackTrace();
        }

        long ageMs = dateOfToday.getTime() - dateOfBirth.getTime();

        long ageYear = ageMs/31556952000L;
        long ageMonth = (ageMs % 31556952000L) / 2629746000L;
        long ageDays = ((ageMs % 31556952000L) % 2629746000L) / 86400000;

        Log.d("Year", "calcAge: " + ageYear +"years, " + ageMonth +"months, " +ageDays + "days");

        String age = ageYear +"v, " + ageMonth +"kk, " +ageDays + "pv";

        return age;
    }

}