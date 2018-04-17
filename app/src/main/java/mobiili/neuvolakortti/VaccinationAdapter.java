package mobiili.neuvolakortti;

/**
 * Created by tanja on 13/04/2018.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class VaccinationAdapter extends RecyclerView.Adapter<VaccinationAdapter.MyViewHolder> {

    private List<Vaccine> vaccineList;
    private DbAdapter db;




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView vaccine_name;
        public TextView date_given;
        public ImageButton button_edit;
        public ImageButton button_delete;

        public MyViewHolder(View view) {
            super(view);
            vaccine_name = (TextView) view.findViewById(R.id.vaccine_name);
            date_given = (TextView) view.findViewById(R.id.date_given);
            //button_edit = (ImageButton)view.findViewById(R.id.button_edit);
            button_delete = (ImageButton)view.findViewById(R.id.button_delete);

        }
    }


    public VaccinationAdapter(List<Vaccine> vaccineList, DbAdapter db) {
        this.vaccineList = vaccineList;
        this.db = db;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vaccination_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Vaccine vaccine = vaccineList.get(position);
        holder.vaccine_name.setText(vaccine.getName());
        holder.date_given.setText(vaccine.getDate());
        //holder.button_edit.setImageResource(android.R.drawable.ic_menu_edit);
        holder.button_delete.setImageResource(android.R.drawable.ic_menu_delete);

        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clickedVaccine = vaccineList.get(position).getName();
                String clickedDate = vaccineList.get(position).getDate();
                String id = vaccineList.get(position).getId();
                Log.d("NIMI:", clickedVaccine);
                Log.d("DATE", clickedDate);
                Log.d("ID", id);
                db.open();
                db.deleteVaccination(id);
                db.close();
                vaccineList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return vaccineList.size();
    }

}
