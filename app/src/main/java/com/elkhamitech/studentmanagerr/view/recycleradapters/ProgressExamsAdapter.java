package com.elkhamitech.studentmanagerr.view.recycleradapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.elkhamitech.studentmanagerr.R;
import com.elkhamitech.studentmanagerr.data.model.OralModel;

import java.util.ArrayList;
import java.util.List;


public abstract class ProgressExamsAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<T> abstractObject;

    public abstract RecyclerView.ViewHolder setViewHolder(ViewGroup parent);

    public abstract void onBindData(RecyclerView.ViewHolder holder, T val);


    public ProgressExamsAdapter(List<T> abstractObject, Context context){
        this.abstractObject = abstractObject;
        this.context = context;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        public TextView gradeText;
        public TextView fromText;
        public ImageButton moreButton;

        public myViewHolder(View itemView) {
            super(itemView);

            gradeText = itemView.findViewById(R.id.exam_grade_text);
            fromText = itemView.findViewById(R.id.exam_from_text);
            moreButton = itemView.findViewById(R.id.exam_more_imageButton);

        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = setViewHolder(parent);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindData(holder,abstractObject.get(position));
    }

    @Override
    public int getItemCount() {
        return abstractObject.size();
    }

    public void addItems( ArrayList<T> savedCardItemz){
        abstractObject = savedCardItemz;
        this.notifyDataSetChanged();
    }

    public T getItem(int position){
        return abstractObject.get(position);
    }



}
