package mobiili.neuvolakortti;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
 * {@link AndroidFlavorAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a data source, which is a list of {@link AndroidFlavor} objects.
 * */
public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.MyViewHolder> {
    public List<Child> myValues;
    Context context;
    public ChildAdapter (List<Child> myValues){
        this.myValues= myValues;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_list_item, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.currentChild = myValues.get(position);
        holder.nameTextView.setText(holder.currentChild.getName());
        holder.ageTextView.setText(calcAge(holder.currentChild.getDateOfBirth()));

        if (holder.currentChild.getPhoto().equals("default")) {
            holder.imageView.setImageResource(R.drawable.ic_face_24dp);
        }
        else {
            holder.imageView.setImageURI(getChildPhotoUri(holder.currentChild.getPhoto(), context));
        }

        final Button button = holder.buttonOptions;

        holder.buttonOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, button);

                popup.inflate(R.menu.child_options_menu);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = holder.currentChild.getId();
                        switch (item.getItemId()) {
                            case R.id.edit_profile:
                                Intent intent = new Intent(context, EditChildProfileActivity.class);
                                intent.putExtra("ID", id);
                                context.startActivity(intent);
                              return true;
                            case R.id.delete_profile:

                                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                                alertDialog.setTitle("Oletko varma?");
                                alertDialog.setMessage("Poistetaanko profiili \""+holder.currentChild.getName()+"\"?\n" +
                                        "Huom! Kaikki profiilin tiedot poistetaan.");

                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Poista",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                // remove child's data from db
                                                DbAdapter db = new DbAdapter(context);
                                                db.open();
                                                db.deleteChild(String.valueOf(holder.currentChild.getId()));
                                                db.close();

                                                // remove profile from list
                                                int pos = holder.getAdapterPosition();
                                                myValues.remove(pos);
                                                notifyDataSetChanged();
                                            }
                                        });
                                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Peruuta",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });

                                alertDialog.show();
                                return true;
                        }
                        return false;
                    }
                });

                popup.show();
            }
        });
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
        public Button buttonOptions;
        private ImageView imageView;

        public MyViewHolder(final View itemView) {
            super(itemView);
            view = itemView;
            //Click listener for profile
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ChildProfileActivity.class);
                    intent.putExtra("ID", currentChild.getId());
                    context.startActivity(intent);
                }
            });
            nameTextView = itemView.findViewById(R.id.tv_child_name);
            ageTextView = itemView.findViewById(R.id.tv_child_age);
            buttonOptions = itemView.findViewById(R.id.tv_options_button);
            imageView = itemView.findViewById(R.id.photo);
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

    public Uri getChildPhotoUri(String photo, Context context) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File file = new File(directory, photo + ".jpg");
        Uri outputUri = Uri.fromFile(file);
        Log.d("TAG", outputUri.toString());

        return outputUri;
    }

}