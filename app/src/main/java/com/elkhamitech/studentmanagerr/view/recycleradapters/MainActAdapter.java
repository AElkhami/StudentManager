package com.elkhamitech.studentmanagerr.view.recycleradapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elkhamitech.studentmanagerr.R;
import com.elkhamitech.studentmanagerr.data.model.MainButtonsModel;

import java.util.List;

/**
 * Created by ElkhamiTech on 2/4/2018.
 */

public class MainActAdapter extends RecyclerView.Adapter<MainActAdapter.myViewHolder>{

    private List<MainButtonsModel> buttonsModels;


    public class myViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linearLayout;
        ImageView mainIcon;
        TextView mainText;

        public myViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.main_item_container);
            mainIcon = itemView.findViewById(R.id.main_act_img);
            mainText = itemView.findViewById(R.id.main_act_text);
        }
    }



    public MainActAdapter(List<MainButtonsModel> mbuttonsModels) {
        this.buttonsModels = mbuttonsModels;
    }


    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_act_list_item,parent,false);


        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        MainButtonsModel mButtons = buttonsModels.get(position);

        holder.linearLayout.setBackgroundColor(mButtons.getbackgroundColor());
        holder.mainIcon.setImageResource(mButtons.getImgIcon());
        holder.mainText.setText(mButtons.getButtonText());
        holder.mainText.setTextColor(mButtons.getTextColor());

    }

    @Override
    public int getItemCount() {
        return buttonsModels.size();
    }
}
