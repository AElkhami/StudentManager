package com.elkhamitech.studentmanagerr.view.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.elkhamitech.studentmanagerr.R;
import com.elkhamitech.studentmanagerr.businesslogic.accesshandler.SessionManager;
import com.elkhamitech.studentmanagerr.data.model.FinalModel;
import com.elkhamitech.studentmanagerr.data.model.MainButtonsModel;
import com.elkhamitech.studentmanagerr.data.model.MidtermModel;
import com.elkhamitech.studentmanagerr.data.model.OralModel;
import com.elkhamitech.studentmanagerr.data.model.QuizModel;
import com.elkhamitech.studentmanagerr.data.model.SubjectsModel;
import com.elkhamitech.studentmanagerr.data.model.UserModel;
import com.elkhamitech.studentmanagerr.data.sqlitehelper.DatabaseHelper;
import com.elkhamitech.studentmanagerr.view.recycleradapters.MainActAdapter;
import com.elkhamitech.studentmanagerr.view.recycleradapters.handlers.RecyclerTouchListener;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Intent goTo;
    private RecyclerView mRecyclerView;

    private DatabaseHelper db;
    private UserModel user;
    private SessionManager mSessionManager;

    private HashMap<String, Long> rid;
    private long row_id;

    private Menu menu;

    private MainButtonsModel mainButtonsModel, mainButtonsModel2, mainButtonsModel3, mainButtonsModel4, mainButtonsModel5;
    private MainActAdapter mainActAdapter;
    private PieChart mPieChart;

    private OralModel oralModel;
    private List<OralModel> oralModelList;
    private QuizModel quizModel;
    private List<QuizModel> quizModelList;
    private MidtermModel midtermModel;
    private List<MidtermModel> midtermModelList;
    private FinalModel finalModel;
    private List<FinalModel> finalModelList;
    private SubjectsModel subjectsModel;
    private List<SubjectsModel> subjectsModelList = new ArrayList<>();;

    private double oralSum;
    private static DecimalFormat df2 = new DecimalFormat(".##");

    private long subjectId;
    private String subjectName;

    private List<MainButtonsModel> mainButtonsModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSessionManager = new SessionManager(this);
        rid = mSessionManager.getRowDetails();
        row_id = rid.get(SessionManager.KEY_ID);

        db = new DatabaseHelper(getApplicationContext());

        mRecyclerView = findViewById(R.id.main_act_recyclerv);
        mPieChart = findViewById(R.id.chart1);

        loadListData();
        loadPieChart();
        loadActionBarImage();

        mainActAdapter = new MainActAdapter(mainButtonsModels);

        mRecyclerView.setAdapter(mainActAdapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

        mainActAdapter.notifyDataSetChanged();

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this.getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                switch (position) {
                    case 0:
                        goTo = new Intent(MainActivity.this, MainSubjectActivity.class);
                        startActivity(goTo);
                        break;
                    case 1:
                        goTo = new Intent(MainActivity.this, MainTodoListActivity.class);
                        startActivity(goTo);
                        break;
                    case 2:
                        goTo = new Intent(MainActivity.this, MainTimeManagerActivity.class);
                        startActivity(goTo);
                        break;
                    case 3:
//                        goTo = new Intent(MainActivity.this, MainProgressReportActivity.class);
//                        startActivity(goTo);
                        break;


                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void loadActionBarImage() {
        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);

        TextView mTitleTextView = mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("Student Manager");

        ImageView imageView = mCustomView
                .findViewById(R.id.imageView);


        loadImageFromDB(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                goTo = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(goTo);
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void loadImageFromDB(ImageView imageView) {


        db = new DatabaseHelper(getApplicationContext());
        user = new UserModel();
        user = db.getLoggedUser(row_id);

        Glide.with(MainActivity.this)
                .load(user.getUserImage()) // add your image url
                .apply(RequestOptions.circleCropTransform()) // applying the image transformer
                .into(imageView);
    }

    private void loadPieChart() {

        subjectsModelList = db.getUserSubject(row_id);

        subjectsModel = new SubjectsModel();

        oralModel = new OralModel();
        quizModel = new QuizModel();
        midtermModel = new MidtermModel();
        finalModel = new FinalModel();

        Map<String, Integer> subjectsMap = new HashMap<>();

        if (!(subjectsModelList == null)) {

            for (int i = 0; i < subjectsModelList.size(); i++) {

                subjectId = subjectsModelList.get(i).getRow_id();
                subjectName = subjectsModelList.get(i).getSubjectName();


                subjectsMap.put(subjectName, getTotal());


            }

        } else {
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }


        List<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 0; i < subjectsModelList.size(); i++) {
            subjectsModel = subjectsModelList.get(i);
            pieEntries.add(new PieEntry(subjectsMap.get(subjectsModel.getSubjectName()), subjectsModel.getSubjectName()));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Student Marks");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextSize(15);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setSliceSpace(5f);


        PieData data = new PieData(pieDataSet);
        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextColor(Color.WHITE);


        mPieChart.setData(data);
//        mPieChart.setEntryLabelColor(Color.WHITE);
        mPieChart.animateY(1000);
        mPieChart.setCenterText("Student Marks");
        mPieChart.getDescription().setEnabled(false);
//        mPieChart.setHoleColor(Color.WHITE);
//        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.invalidate();

        Legend legend = mPieChart.getLegend();
        legend.setTextColor(Color.WHITE);
        legend.setTextSize(15);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
//        legend.setEnabled(false);

    }

    private void loadListData() {
        mainButtonsModel = new MainButtonsModel();
        mainButtonsModel.setbackgroundColor(ContextCompat.getColor(this, R.color.a));
        mainButtonsModel.setImgIcon(R.drawable.ic_subject);
        mainButtonsModel.setButtonText("Subject");
        mainButtonsModel.setTextColor(Color.WHITE);

        mainButtonsModel2 = new MainButtonsModel();
        mainButtonsModel2.setbackgroundColor(ContextCompat.getColor(this, R.color.b));
        mainButtonsModel2.setImgIcon(R.drawable.ic_todo);
        mainButtonsModel2.setButtonText("Todo List");
        mainButtonsModel2.setTextColor(Color.WHITE);

        mainButtonsModel3 = new MainButtonsModel();
        mainButtonsModel3.setbackgroundColor(ContextCompat.getColor(this, R.color.c));
        mainButtonsModel3.setImgIcon(R.drawable.ic_time);
        mainButtonsModel3.setButtonText("Time Manager");
        mainButtonsModel3.setTextColor(Color.WHITE);

        mainButtonsModel4 = new MainButtonsModel();
        mainButtonsModel4.setbackgroundColor(ContextCompat.getColor(this, R.color.d));
        mainButtonsModel4.setImgIcon(R.drawable.ic_progress);
        mainButtonsModel4.setButtonText("Progress Monitor");
        mainButtonsModel4.setTextColor(Color.WHITE);

        mainButtonsModels.add(mainButtonsModel);
        mainButtonsModels.add(mainButtonsModel2);
        mainButtonsModels.add(mainButtonsModel3);
        mainButtonsModels.add(mainButtonsModel4);
    }


    public int getTotal() {

        double OralValue = 0;
        double OralFrom = 0;

        double quizValue = 0;
        double quizFrom = 0;

        double midValue = 0;
        double midFrom = 0;

        double finalValue = 0;
        double finalFrom = 0;


        oralModelList = db.getOral(subjectId);
        quizModelList = db.getQuiz(subjectId);
        midtermModelList = db.getMidterm(subjectId);
        finalModelList = db.getFinal(subjectId);

        if (!(oralModelList == null)) {

            for (int i = 0; i < oralModelList.size(); i++) {
                OralValue += Double.valueOf(oralModelList.get(i).getValue());
                OralFrom += Double.valueOf(oralModelList.get(i).getValueFrom());
            }

        }
        if (!(quizModelList == null)) {

            for (int i = 0; i < quizModelList.size(); i++) {
                quizValue += Integer.valueOf(quizModelList.get(i).getValue());
                quizFrom += Integer.valueOf(quizModelList.get(i).getValueFrom());
            }

        }
        if (!(midtermModelList == null)) {

            for (int i = 0; i < midtermModelList.size(); i++) {
                midValue += Integer.valueOf(midtermModelList.get(i).getValue());
                midFrom += Integer.valueOf(midtermModelList.get(i).getValueFrom());
            }

        }
        if (!(finalModelList == null)) {

            for (int i = 0; i < finalModelList.size(); i++) {
                finalValue += Integer.valueOf(finalModelList.get(i).getValue());
                finalFrom += Integer.valueOf(finalModelList.get(i).getValueFrom());
            }

        }

        double total1 = OralValue + quizValue + midValue + finalValue;
        double total2 = OralFrom + quizFrom + midFrom + finalFrom;

//        tv1.setText(subjectName +" : "+ String.valueOf(subjectId));
//        tv2.setText(String.valueOf(total1)+ " / "+ String.valueOf(total2));
        oralSum = total1 / total2 * 100;
//        tv3.setText(String.valueOf(df2.format(oralSum)) + " %");

        return Integer.valueOf((int) oralSum);
    }


}


