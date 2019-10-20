package com.elkhamitech.studentmanagerr.view.recycleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elkhamitech.studentmanagerr.view.activities.MainActivity;
import com.elkhamitech.studentmanagerr.R;
import com.elkhamitech.studentmanagerr.data.model.ProgressModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

import java.util.List;

/**
 * Created by ElkhamiTech on 2/4/2018.
 */

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.myViewHolder> {

    private List<ProgressModel> progresssModels;

    private Context mContext;
    MainActivity ma;

    public class myViewHolder extends RecyclerView.ViewHolder{

        BarChart mBarChart;
        TextView mTextView;

        public myViewHolder(View itemView) {
            super(itemView);
            mBarChart = itemView.findViewById(R.id.chart2);
            mTextView = itemView.findViewById(R.id.progress_title_txtv);

        }
    }



    public ProgressAdapter(List<ProgressModel> progresssModels) {
        this.progresssModels = progresssModels;
    }


    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_list_item,parent,false);


        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {


        ProgressModel mChart = progresssModels.get(position);

        holder.mTextView.setText(mChart.getTitle());

        holder.mBarChart.getDescription().setEnabled(false);
        holder.mBarChart.setDrawGridBackground(false);
        holder.mBarChart.setDrawBarShadow(false);

        XAxis xAxis = holder.mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);


        YAxis leftAxis = holder.mBarChart.getAxisLeft();
        leftAxis.setLabelCount(5, false);
        leftAxis.setSpaceTop(20f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setValueFormatter(mChart.formatter);
//        leftAxis.setGranularity(1f);

        YAxis rightAxis = holder.mBarChart.getAxisRight();
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(20f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)


        // set data
        holder.mBarChart.setData(mChart.getBarData());
        holder.mBarChart.setFitBars(true);

        // do not forget to refresh the chart
        holder.mBarChart.invalidate();
        holder.mBarChart.animateY(700);



    }

    @Override
    public int getItemCount() {
        return progresssModels.size();
    }
}
