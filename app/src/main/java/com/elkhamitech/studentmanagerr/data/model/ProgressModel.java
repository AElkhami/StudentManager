package com.elkhamitech.studentmanagerr.data.model;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by ElkhamiTech on 2/14/2018.
 */

public class ProgressModel {

    private int [] marks;
    private String [] subjects;
    private BarData barData;
    private int size;
    private String title;

    public ProgressModel() {
    }


    public int[] getMarks() {
        return marks;
    }

    public void setMarks(int[] marks) {
        this.marks = marks;
    }

    public String[] getSubjects() {
        return subjects;
    }

    public void setSubjects(String[] subjects) {
        this.subjects = subjects;
    }

    public BarData getBarData() {
        return barData;
    }

    public void setBarData(BarData barData) {
        this.barData = barData;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public IAxisValueFormatter formatter = new IAxisValueFormatter() {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {

            return subjects[(int) value];
        }



    };
}
