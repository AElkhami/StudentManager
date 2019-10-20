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
import com.elkhamitech.studentmanagerr.view.recycleradapters.TaskAdapter;
import com.elkhamitech.studentmanagerr.R;
import com.elkhamitech.studentmanagerr.data.sqlitehelper.DatabaseHelper;
import com.elkhamitech.studentmanagerr.data.model.TaskModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainTodoListActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private FloatingActionButton mFAB;
    private List<TaskModel> mTasks = new ArrayList<>();
    private TaskModel taskModel;
    private TaskAdapter mAdapter;
    private Intent goTo;

    private DatabaseHelper db;
    private SessionManager mSessionManager;

    private HashMap<String, Long> rid;
    private long row_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_todo_list);

        getSupportActionBar().setTitle("Todo List");

        mRecyclerView = findViewById(R.id.recycler_view_list);
        mFAB = findViewById(R.id.add_list_fab);

        db = new DatabaseHelper(this);
        mSessionManager = new SessionManager(this);

        taskModel = new TaskModel();

        rid= mSessionManager.getRowDetails();

        row_id = rid.get(SessionManager.KEY_ID);

        mTasks = db.getUserTasks(row_id);

        mAdapter = new TaskAdapter(mTasks);
        mRecyclerView.setAdapter(mAdapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

        mAdapter.notifyDataSetChanged();

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this.getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TaskModel model = mTasks.get(position);

                }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void addList(View view) {

        taskModel.setTaskName("Title");
        taskModel.setStudent_task(row_id);

        long task_id = db.createTask(taskModel);

        goTo = new Intent(MainTodoListActivity.this, TodoListItemActivity.class);
        goTo.putExtra("long", task_id);
        startActivity(goTo);

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Refresh Recycler View after resuming
        mTasks.clear();
        mTasks.addAll(db.getUserTasks(row_id));
        mAdapter =  new TaskAdapter(mTasks);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


    }


}
