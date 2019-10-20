package com.elkhamitech.studentmanagerr.view.recycleradapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.elkhamitech.studentmanagerr.R;
import com.elkhamitech.studentmanagerr.data.model.SubjectsModel;
import com.elkhamitech.studentmanagerr.data.sqlitehelper.DatabaseHelper;
import com.elkhamitech.studentmanagerr.view.activities.EditSubjectActivity;
import com.elkhamitech.studentmanagerr.view.activities.MainSubjectActivity;
import com.elkhamitech.studentmanagerr.view.activities.SubjectMarksMainActivity;

import java.util.List;

/**
 * Created by ElkhamiTech on 1/28/2018.
 */

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.myViewHolder> {

    private List<SubjectsModel> subjectsList;
    private Context context;

    public class myViewHolder extends RecyclerView.ViewHolder {

        private long row_id;

        private TextView subjectName;
        private TextView classLocation;
        private TextView time;
        private TextView date;
        private ImageView subjectImage;
        private ImageButton moreButton;
        private Intent goTo;
        private SubjectsModel mSubjectsModel;


        public myViewHolder(View itemView) {
            super(itemView);

            subjectName = itemView.findViewById(R.id.subject_name_txtv);
            classLocation = itemView.findViewById(R.id.subject_location_txtv);
            time = itemView.findViewById(R.id.subject_time_txtv);
            date = itemView.findViewById(R.id.subject_date_txtv);
            subjectImage = itemView.findViewById(R.id.subject_imgv);
            moreButton = itemView.findViewById(R.id.subject_more_imageButton);


        }
    }

    public SubjectsAdapter(List<SubjectsModel> mSubjectsModel) {

        this.subjectsList = mSubjectsModel;

    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_list_item, parent, false);

        context = parent.getContext();
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {
        final SubjectsModel mSubjects = subjectsList.get(position);

        holder.row_id = mSubjects.getRow_id();

        final DatabaseHelper db = new DatabaseHelper(context);

        holder.subjectName.setText(mSubjects.getSubjectName());
        holder.classLocation.setText(mSubjects.getClassLocation());
        holder.time.setText(mSubjects.getTime());
        holder.date.setText(mSubjects.getDay() + " .");

        holder.moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(context, holder.moreButton);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.item_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.edit_item:

                                Intent goTo = new Intent(context, EditSubjectActivity.class);
                                goTo.putExtra("subjectRowId", holder.row_id);
                                context.startActivity(goTo);


                                return true;
                            case R.id.delete_item:

                                new AlertDialog.Builder(context)
                                        .setTitle("Delete entry")
                                        .setMessage("Are you sure you want to delete this subject?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // continue with delete
                                                db.deleteSubject(holder.row_id);
                                                subjectsList.remove(position);
                                                notifyDataSetChanged();
//
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing

                                            }
                                        })
//                .setIcon(android.R.drawable.ic_dialog_info)
                                        .show();


                                return true;
                            default:
                                return false;
                        }

                    }
                });

                popup.show();//showing popup menu
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.goTo = new Intent(context,SubjectMarksMainActivity.class);
                holder.goTo.putExtra("subjectRowId",mSubjects.getRow_id());
                context.startActivity(holder.goTo);
            }
        });

    }

    @Override
    public int getItemCount() {
        return subjectsList.size();
    }


}
