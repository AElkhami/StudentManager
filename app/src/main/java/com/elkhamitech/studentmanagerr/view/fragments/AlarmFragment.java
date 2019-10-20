package com.elkhamitech.studentmanagerr.view.fragments;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.elkhamitech.studentmanagerr.businesslogic.notifications.AlarmReceiver;
import com.elkhamitech.studentmanagerr.R;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends Fragment {

    private FloatingActionButton startStopAlarm;
    private Button cancelAalarml;
    private ImageView playImage;
    private TextView secondText;
    private CheckBox optRepeat20;

    TextView textAlarmPrompt;
    TimePickerDialog timePickerDialog;
    final static int RQS_1 = 1;


    public AlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarm, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Activity activity = getActivity();

        startStopAlarm = activity.findViewById(R.id.start_stop_alarm_btn);
        cancelAalarml= activity.findViewById(R.id.cancel_alarm_btn);
        textAlarmPrompt= activity.findViewById(R.id.alarm_numbers_txtv);
        optRepeat20= activity.findViewById(R.id.repeat_chekbox);
        playImage = activity.findViewById(R.id.play_image_alarm);
        secondText = activity.findViewById(R.id.second_text_alarm);

        startStopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textAlarmPrompt.setText("");
                openTimePickerDialog(false);
                setHasOptionsMenu(true);


            }
        });


        cancelAalarml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });




    }



    private void openTimePickerDialog(boolean is24r) {
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(getActivity(),
                onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), is24r);
        timePickerDialog.setTitle("Set Alarm Time");

        timePickerDialog.show();

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
            }

            setAlarm(calSet, optRepeat20.isChecked());

        }
    };

    private void setAlarm(Calendar targetCal, Boolean repeat) {

        textAlarmPrompt.setText("Alarm is Set at " + targetCal.getTime());

        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),
                RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity()
                .getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                pendingIntent);

        if(repeat){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    targetCal.getTimeInMillis(),
                    TimeUnit.MINUTES.toMillis(20),
                    pendingIntent);

            textAlarmPrompt.setText("Alarm is set at\n\n" + targetCal.getTime()
                    +"\n\nRepeat every 20 minute");
            playImage.setVisibility(View.GONE);
            secondText.setVisibility(View.GONE);
        }else{

            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    targetCal.getTimeInMillis(),
                    pendingIntent);

            textAlarmPrompt.setText("Alarm is set at\n" + targetCal.getTime()+
                    "\n\nOne shot");
            playImage.setVisibility(View.GONE);
            secondText.setVisibility(View.GONE);
        }

    }

    private void cancelAlarm() {

        textAlarmPrompt.setText("Alarm Cancelled!\n");

        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),
                RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity()
                .getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

    }
}
