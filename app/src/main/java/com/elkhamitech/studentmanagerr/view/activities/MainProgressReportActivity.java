package com.elkhamitech.studentmanagerr.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elkhamitech.studentmanagerr.R;
import com.elkhamitech.studentmanagerr.businesslogic.accesshandler.SessionManager;
import com.elkhamitech.studentmanagerr.data.model.FinalModel;
import com.elkhamitech.studentmanagerr.data.model.MidtermModel;
import com.elkhamitech.studentmanagerr.data.model.OralModel;
import com.elkhamitech.studentmanagerr.data.model.QuizModel;
import com.elkhamitech.studentmanagerr.data.model.SubjectsModel;
import com.elkhamitech.studentmanagerr.data.sqlitehelper.DatabaseHelper;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainProgressReportActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_progress_report);
        getSupportActionBar().setTitle("Progress Report");


    }


}
