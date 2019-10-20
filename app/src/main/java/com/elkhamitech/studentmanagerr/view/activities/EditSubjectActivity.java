package com.elkhamitech.studentmanagerr.view.activities;

import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import com.codetroopers.betterpickers.recurrencepicker.EventRecurrence;
import com.codetroopers.betterpickers.recurrencepicker.EventRecurrenceFormatter;
import com.codetroopers.betterpickers.recurrencepicker.RecurrencePickerDialogFragment;
import com.elkhamitech.studentmanagerr.R;
import com.elkhamitech.studentmanagerr.data.model.SubjectsModel;
import com.elkhamitech.studentmanagerr.data.sqlitehelper.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditSubjectActivity extends AppCompatActivity implements RecurrencePickerDialogFragment.OnRecurrenceSetListener{

    EditText subjectName;
    EditText subjectLocation;
    EditText subjectDay;
    EditText subjectTime;
    DatabaseHelper db;
    SubjectsModel subject;
    long id;

    private static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";
    private String mRrule;
    private EventRecurrence mEventRecurrence = new EventRecurrence();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subject);

        subjectName = findViewById(R.id.name_edtxt_edit);
        subjectLocation = findViewById(R.id.loc_edtxt_edit);
        subjectDay = findViewById(R.id.day_edtxt_edit);
        subjectTime = findViewById(R.id.time_edtxt_edit);

        loadSubjectDetails();
    }

    private void loadSubjectDetails() {

        db = new DatabaseHelper(this);
        subject = new SubjectsModel();

        id = getIntent().getLongExtra("subjectRowId",0);

        subject = db.getSubject(id);

        subjectName.setText(subject.getSubjectName());
        subjectLocation.setText(subject.getClassLocation());
        subjectDay.setText(subject.getDay());
        subjectTime.setText(subject.getTime());




        subjectDay.setOnClickListener(new View.OnClickListener() {
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
                dialogFragment.setOnRecurrenceSetListener(EditSubjectActivity.this);
                dialogFragment.show(fm, FRAG_TAG_TIME_PICKER);

            }

        });

        subjectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();

                TimePickerDialog timePickerDialog = new TimePickerDialog(EditSubjectActivity.this,
                        onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), false);
                timePickerDialog.setTitle("Set lecture time");

                timePickerDialog.show();

            }

        });
    }

    private void updateSubject(){

        subject.setSubjectName(subjectName.getText().toString());
        subject.setClassLocation(subjectLocation.getText().toString());
        subject.setDay(subjectDay.getText().toString());
        subject.setTime(subjectTime.getText().toString());

        db.updateSubject(subject);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_menu:
                updateSubject();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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

            if (calSet.compareTo(calNow) <= 0) {
                // Today Set time passed, count to tomorrow
                calSet.add(Calendar.DATE, 1);

                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
                subjectTime.setText(dateFormat.format(calSet.getTime()));
            }


        }
    };

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

        subjectDay.setText(repeatString);

    }
}
