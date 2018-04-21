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
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_LIST = 1;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView vaccine_name;
        public TextView date_given;
        public TextView headerTitle;
        public TextView headerDate;
        public ImageButton button_delete;
        int view_type;

        public MyViewHolder(View view, int viewType) {
            super(view);

            if( viewType == TYPE_LIST)
            {
                vaccine_name = (TextView) view.findViewById(R.id.vaccine_name);
                date_given = (TextView) view.findViewById(R.id.date_given);
                button_delete = (ImageButton)view.findViewById(R.id.button_delete);
                view_type = 1;
            }

            else if( viewType == TYPE_HEAD)
            {
                headerTitle = (TextView)view.findViewById(R.id.header_title);
                headerDate = (TextView)view.findViewById(R.id.header_date);
                view_type = 0;
            }


        }
    }


    public VaccinationAdapter(List<Vaccine> vaccineList, DbAdapter db) {
        this.vaccineList = vaccineList;
        this.db = db;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        if(viewType == TYPE_LIST)
        {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vaccination_list_row, parent, false);
            return new MyViewHolder(itemView, viewType);
        }

        else if(viewType == TYPE_HEAD)
        {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vaccination_header, parent, false);
            return new MyViewHolder(itemView, viewType);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if(holder.view_type == TYPE_LIST)
        {
            Vaccine vaccine = vaccineList.get(position - 1);
            holder.vaccine_name.setText(vaccine.getName());
            holder.date_given.setText(vaccine.getDate());
            holder.button_delete.setImageResource(android.R.drawable.ic_menu_delete);

            holder.button_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String clickedVaccine = vaccineList.get(position -1).getName();
                    String clickedDate = vaccineList.get(position -1).getDate();
                    String id = vaccineList.get(position -1).getId();
                    Log.d("NIMI:", clickedVaccine);
                    Log.d("DATE", clickedDate);
                    Log.d("ID", id);
                    db.open();
                    db.deleteVaccination(id);
                    db.close();
                    vaccineList.remove(position - 1);
                    notifyItemRemoved(position );
                    notifyItemRangeChanged((position - 1), getItemCount());
                }
            });

        }

        else if(holder.view_type == TYPE_HEAD)
        {
            holder.headerTitle.setText(R.string.vaccine);
            holder.headerDate.setText(R.string.date);

        }

    }

    @Override
    public int getItemCount() {
        return vaccineList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if ( position == 0)
        {
            return TYPE_HEAD;
        }
        return TYPE_LIST;
    }
}
