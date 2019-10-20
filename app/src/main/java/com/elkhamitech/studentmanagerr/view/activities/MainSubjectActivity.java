package com.elkhamitech.studentmanagerr.view.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.elkhamitech.studentmanagerr.businesslogic.accesshandler.SessionManager;
import com.elkhamitech.studentmanagerr.view.recycleradapters.handlers.RecyclerTouchListener;
import com.elkhamitech.studentmanagerr.view.recycleradapters.SubjectsAdapter;
import com.elkhamitech.studentmanagerr.R;
import com.elkhamitech.studentmanagerr.data.sqlitehelper.DatabaseHelper;
import com.elkhamitech.studentmanagerr.data.model.SubjectsModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainSubjectActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<SubjectsModel> mSubjects = new ArrayList<>();
    private SubjectsAdapter mAdapter;
    private Intent goTo;
    private SubjectsModel mSubjectsModel;
    private DatabaseHelper db;

    private long row_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_subject);

        getSupportActionBar().setTitle("Subjects");

        mRecyclerView = findViewById(R.id.recycler_view_subject);
        FloatingActionButton mFAB = findViewById(R.id.add_subject_fab);

        db = new DatabaseHelper(this);
        SessionManager mSessionManager = new SessionManager(this);



        HashMap<String, Long> rid = mSessionManager.getRowDetails();

        row_id = rid.get(SessionManager.KEY_ID);

        mSubjects = db.getUserSubject(row_id);



        mAdapter = new SubjectsAdapter(mSubjects);
        mRecyclerView.setAdapter(mAdapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

        mAdapter.notifyDataSetChanged();
    }

    public void addSubject(View view) {

        goTo = new Intent(MainSubjectActivity.this,CreateNewSubjectActivity.class);
        startActivity(goTo);

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Refresh Recycler View after resuming
        mSubjects.clear();
        mSubjects.addAll(db.getUserSubject(row_id));
        mAdapter =  new SubjectsAdapter(mSubjects);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


    }
}
