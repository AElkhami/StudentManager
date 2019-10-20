package com.elkhamitech.studentmanagerr.view.activities;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.text.format.Time;
import android.widget.Toast;

import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.codetroopers.betterpickers.recurrencepicker.EventRecurrence;
import com.codetroopers.betterpickers.recurrencepicker.EventRecurrenceFormatter;
import com.codetroopers.betterpickers.recurrencepicker.RecurrencePickerDialogFragment;
import com.elkhamitech.studentmanagerr.businesslogic.accesshandler.SessionManager;
import com.elkhamitech.studentmanagerr.R;
import com.elkhamitech.studentmanagerr.data.sqlitehelper.DatabaseHelper;
import com.elkhamitech.studentmanagerr.data.model.SubjectsModel;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CreateNewSubjectActivity extends AppCompatActivity implements RecurrencePickerDialogFragment.OnRecurrenceSetListener {

    private EditText subjectName;
    private EditText classLocation;
    private EditText day;
    private EditText timeEdtxt;
    private EventRecurrence mEventRecurrence = new EventRecurrence();

    DatabaseHelper db;
    SubjectsModel mSubjectsModel;

    private static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";
    private String mRrule;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_subject);

        getSupportActionBar().setTitle("Create New Subject");

        subjectName = findViewById(R.id.name_edtxt_subject);
        classLocation = findViewById(R.id.loc_edtxt_subject);
        day = findViewById(R.id.day_edtxt_subject);
        timeEdtxt = findViewById(R.id.time_edtxt_subject);


        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                Bundle bundle = new Bundle();
                Time time = new Time();
                time.setToNow();
                bundle.putLong(RecurrencePickerDialogFragment.BUNDLE_START_TIME_MILLIS, time.toMillis(false));
                bundle.putString(RecurrencePickerDialogFragment.BUNDLE_TIME_ZONE, time.timezone);
                bundle.putBoolean(RecurrencePickerDialogFragment.BUNDLE_HIDE_SWITCH_BUTTON, true);

                // may be more efficient to serialize and pass in EventRecurrence
                bundle.putString(RecurrencePickerDialogFragment.BUNDLE_RRULE, mRrule);

                RecurrencePickerDialogFragment dialogFragment = (RecurrencePickerDialogFragment) fm.findFragmentByTag(FRAG_TAG_TIME_PICKER);
                if (dialogFragment != null) {
                    dialogFragment.dismiss();
                }
                dialogFragment = new RecurrencePickerDialogFragment();
                dialogFragment.setArguments(bundle);
                dialogFragment.setOnRecurrenceSetListener(CreateNewSubjectActivity.this);
                dialogFragment.show(fm, FRAG_TAG_TIME_PICKER);

            }

        });

        timeEdtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();

                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateNewSubjectActivity.this,
                        onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), false);
                timePickerDialog.setTitle("Set lecture time");

                timePickerDialog.show();

            }

        });


    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);


                // Today Set time passed, count to tomorrow
                calSet.add(Calendar.DATE, 1);

                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                timeEdtxt.setText(dateFormat.format(calSet.getTime()));



        }
    };

    public void createSubject(View view) {

        DatabaseHelper db = new DatabaseHelper(this);
        mSubjectsModel = new SubjectsModel();


        SessionManager mSessionManager = new SessionManager(this);
        HashMap<String, Long> rid = mSessionManager.getRowDetails();
        long row_id = rid.get(SessionManager.KEY_ID);

        mSubjectsModel.setSubjectName(subjectName.getText().toString());
        mSubjectsModel.setClassLocation(classLocation.getText().toString());
        mSubjectsModel.setDay(day.getText().toString());
        mSubjectsModel.setTime(timeEdtxt.getText().toString());
        mSubjectsModel.setUser_id(row_id);

        db.createSubject(mSubjectsModel);

        finish();

    }


    @Override
    public void onRecurrenceSet(String rrule) {
        mRrule = rrule;
        if (mRrule != null) {
            mEventRecurrence.parse(mRrule);
        }
        populateRepeats();
    }

    private void populateRepeats() {
        Resources r = getResources();
        String repeatString = "";
        boolean enabled;
        if (!TextUtils.isEmpty(mRrule)) {
            repeatString = EventRecurrenceFormatter.getRepeatString(this, r, mEventRecurrence, true);
        }

        day.setText(repeatString);
//        Toast.makeText(this, mRrule, Toast.LENGTH_SHORT).show();

    }
}
