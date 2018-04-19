package mobiili.neuvolakortti;

/**
 * Created by tanja on 13/04/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class DevelopmentAdapter extends RecyclerView.Adapter<DevelopmentAdapter.MyViewHolder> {

    private List<Development> developmentList;
    private DbAdapter db;




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView development_name;
        public TextView date_reached;
        public ImageButton button_edit;
        public ImageButton button_delete;

        public MyViewHolder(View view) {
            super(view);
            development_name = (TextView) view.findViewById(R.id.development_name);
            date_reached = (TextView) view.findViewById(R.id.date_reached);
            //button_edit = (ImageButton)view.findViewById(R.id.button_edit);
            button_delete = (ImageButton)view.findViewById(R.id.develop_button_delete);

        }
    }


    public DevelopmentAdapter(List<Development> developmentList, DbAdapter db) {
        this.developmentList = developmentList;
        this.db = db;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.development_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Development development = developmentList.get(position);
        holder.development_name.setText(development.getName());
        holder.date_reached.setText(development.getDate());
        //holder.button_edit.setImageResource(android.R.drawable.ic_menu_edit);
        holder.button_delete.setImageResource(android.R.drawable.ic_menu_delete);

        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clickedDevelopment = developmentList.get(position).getName();
                String clickedDate = developmentList.get(position).getDate();
                String id = developmentList.get(position).getId();
                Log.d("NIMI:", clickedDevelopment);
                Log.d("DATE", clickedDate);
                Log.d("ID", id);
                db.open();
                db.deleteDevelopment(id);
                db.close();
                developmentList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return developmentList.size();
    }

}
