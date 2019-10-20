package com.elkhamitech.studentmanagerr.view.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.elkhamitech.studentmanagerr.data.model.TaskModel;
import com.elkhamitech.studentmanagerr.view.recycleradapters.TaskItemAdapter;
import com.elkhamitech.studentmanagerr.R;
import com.elkhamitech.studentmanagerr.data.sqlitehelper.DatabaseHelper;
import com.elkhamitech.studentmanagerr.data.model.TaskItemModel;

import java.util.ArrayList;
import java.util.List;

public class TodoListItemActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private EditText itemEditText;
    private EditText editText;
    private List<TaskItemModel> mItems = new ArrayList<>();
    private TaskItemModel itemModel;
    private TaskItemAdapter mAdapter;
    private Intent goTo;
    private TaskModel taskModel;

    private DatabaseHelper db;

    private long fk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_item);

        fk = getIntent().getLongExtra("long", 1L);

        mRecyclerView = findViewById(R.id.recycler_view_item);
        itemEditText = findViewById(R.id.item_edtxt);

        db = new DatabaseHelper(this);

        itemModel = new TaskItemModel();

        mItems = db.getTaskItems(fk);

        mAdapter = new TaskItemAdapter(mItems,this);
        mRecyclerView.setAdapter(mAdapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

        mAdapter.notifyDataSetChanged();

        loadActionBarImage();
    }

    public void addNewItem(View view) {
         fk = getIntent().getLongExtra("long", 1L);


        itemModel = new TaskItemModel();

        itemModel.setIsDone("false");
        itemModel.setTask_item(fk);
        itemModel.setItemName(itemEditText.getText().toString());

        db.createItem(itemModel);

        mItems.add(itemModel);
        mAdapter.notifyDataSetChanged();
        itemEditText.getText().clear();

    }



    @Override
    protected void onResume() {
        super.onResume();

        //Refresh Recycler View after resuming
        mItems.clear();
        mItems.addAll(db.getTaskItems(fk));
        mAdapter =  new TaskItemAdapter(mItems,this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

    private void loadActionBarImage() {
        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);

        editText = mCustomView.findViewById(R.id.action_bar_editText);
        editText.setVisibility(View.VISIBLE);

        taskModel = db.getTask(fk);
        String titleText = taskModel.getTaskName();

//        if(titleText.equals("Title")){
//
//            taskModel.setTaskName("Title");
//
//        }else {
//            editText.setText(titleText);
//        }

        editText.setText(titleText);

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                String taskTitle = editText.getText().toString();
                taskModel = new TaskModel();
                taskModel.setTaskName(taskTitle);

                db.updateTask(taskModel,fk);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String taskTitle = editText.getText().toString();
        taskModel = new TaskModel();
        taskModel.setTaskName(taskTitle);

        db.updateTask(taskModel,fk);
    }
}
