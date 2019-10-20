package com.elkhamitech.studentmanagerr.view.recycleradapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.elkhamitech.studentmanagerr.R;
import com.elkhamitech.studentmanagerr.data.model.TaskModel;
import com.elkhamitech.studentmanagerr.data.sqlitehelper.DatabaseHelper;
import com.elkhamitech.studentmanagerr.view.activities.MainTodoListActivity;
import com.elkhamitech.studentmanagerr.view.activities.TodoListItemActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ElkhamiTech on 2/12/2018.
 */

public class TaskAdapter extends RecyclerView.Adapter< TaskAdapter.myViewHolder> {

    private List<TaskModel> modelList = new ArrayList<>();
    private Context context;
    private Intent goTo;


    public static class myViewHolder extends RecyclerView.ViewHolder{

        private long row_id;
        private TextView taskName;
        private ImageButton moreButton;

        public myViewHolder(View itemView) {
            super(itemView);

            taskName = itemView.findViewById(R.id.task_name_txtv);
            moreButton = itemView.findViewById(R.id.task_more_imageButton);
        }
    }

    public TaskAdapter(List<TaskModel> modelList) {
        this.modelList = modelList;
    }


    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item,parent,false);
        context = parent.getContext();
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {
        final TaskModel task = modelList.get(position);
        holder.row_id = task.getRow_id();
        holder.taskName.setText(task.getTaskName());

        final DatabaseHelper db = new DatabaseHelper(context);

        holder.moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(context, holder.moreButton);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.todo_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){
                            case R.id.delete_item:

                                db.deleteTask( holder.row_id);
                                modelList.remove(position);
                                notifyDataSetChanged();

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
                goTo = new Intent(context, TodoListItemActivity.class);
                goTo.putExtra("long",task.getRow_id());
                context.startActivity(goTo);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


}
