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
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_LIST = 1;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView development_name;
        public TextView date_reached;
        public TextView headerTitle;
        public TextView headerDate;
        public ImageButton button_delete;
        int view_type;

        public MyViewHolder(View view, int viewType) {
            super(view);

            if( viewType == TYPE_LIST)
            {
                development_name = (TextView) view.findViewById(R.id.development_name);
                date_reached = (TextView) view.findViewById(R.id.date_reached);
                button_delete = (ImageButton) view.findViewById(R.id.develop_button_delete);
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


    public DevelopmentAdapter(List<Development> developmentList, DbAdapter db) {
        this.developmentList = developmentList;
        this.db = db;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        if(viewType == TYPE_LIST)
        {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.development_list_row, parent, false);
            return new MyViewHolder(itemView, viewType);
        }

        else if(viewType == TYPE_HEAD)
        {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.development_header, parent, false);
            return new MyViewHolder(itemView, viewType);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if(holder.view_type == TYPE_LIST)
        {
            Development development = developmentList.get(position - 1);
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
                    developmentList.remove(position -1);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged((position -1), getItemCount());
                }
            });

        }

        else if(holder.view_type == TYPE_HEAD)
        {
            holder.headerTitle.setText(R.string.development);
            holder.headerDate.setText(R.string.date);

        }
    }

    @Override
    public int getItemCount() {
        return developmentList.size() + 1;
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
