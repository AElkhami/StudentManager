package com.elkhamitech.studentmanagerr.view.recycleradapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.elkhamitech.studentmanagerr.R;
import com.elkhamitech.studentmanagerr.data.sqlitehelper.DatabaseHelper;
import com.elkhamitech.studentmanagerr.data.model.TaskItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ElkhamiTech on 2/12/2018.
 */

public class TaskItemAdapter extends RecyclerView.Adapter<TaskItemAdapter.myViewHolder>{

    private List<TaskItemModel> itemModels = new ArrayList<>();
    private Context context;


    public class myViewHolder extends RecyclerView.ViewHolder{

        private long row_id;
        private EditText itemName;
        private CheckBox isDoneBox;
        private ImageButton moreButton;
        private ImageButton saveBtn;
        private ImageButton cancelBtn;

        public myViewHolder(View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.item_name);
            isDoneBox = itemView.findViewById(R.id.done_checkbox);
            moreButton = itemView.findViewById(R.id.item_more_imageButton);
            saveBtn = itemView.findViewById(R.id.save_todo_item_btn);
            cancelBtn = itemView.findViewById(R.id.cancel_todo_item);

        }
    }

    public TaskItemAdapter(List<TaskItemModel> itemModels,Context mContext) {
        this.itemModels = itemModels;
        this.context = mContext;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_item,parent,false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {
       final TaskItemModel itemModel = itemModels.get(position);
       final DatabaseHelper db = new DatabaseHelper(context);

        holder.row_id = itemModel.getRow_id();
        holder.itemName.setText(itemModel.getItemName());

        if(itemModel.getIsDone().equals("true")){
            holder.isDoneBox.setChecked(true);
        }else if(itemModel.getIsDone().equals("false")){
            holder.isDoneBox.setChecked(false);
        }

        if(itemModel.getIsDone().equals("true")){
            holder.itemName.setPaintFlags(holder.itemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else if(itemModel.getIsDone().equals("false")){
            holder.itemName.setPaintFlags(0);

        }

        holder.isDoneBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemModel.getIsDone().equals("true")){
                    itemModel.setIsDone("false");
                    db.updateItemCheck(itemModel);
                    holder.itemName.setPaintFlags(0);
                }else if(itemModel.getIsDone().equals("false")){
                    itemModel.setIsDone("true");
                    db.updateItemCheck(itemModel);
                    holder.itemName.setPaintFlags(holder.itemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        });


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
//                            case R.id.edit_item:
//
//                                holder.isDoneBox.setVisibility(View.GONE);
//                                holder.moreButton.setVisibility(View.GONE);
//
//                                holder.saveBtn.setVisibility(View.VISIBLE);
//                                holder.cancelBtn.setVisibility(View.VISIBLE);
//
//
//                                return true;
                            case R.id.delete_item:

                                db.deleteItem( holder.row_id);
                                itemModels.remove(position);
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

        holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.isDoneBox.setVisibility(View.VISIBLE);
                holder.moreButton.setVisibility(View.VISIBLE);

                holder.saveBtn.setVisibility(View.GONE);
                holder.cancelBtn.setVisibility(View.GONE);
            }
        });





    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }
}
