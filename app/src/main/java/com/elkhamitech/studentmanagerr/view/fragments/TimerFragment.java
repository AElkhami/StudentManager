package com.elkhamitech.studentmanagerr.view.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.elkhamitech.studentmanagerr.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimerFragment extends Fragment {

    private FloatingActionButton startStopTimer;
    private Button resetTimer;
    private TextView timerValue;
    private long startTime = 0L;
    private long timeInMilliseconds = 0L;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;
    private boolean isStarted = false;

    private Handler customHandler = new Handler();


    public TimerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity activity = getActivity();
        startStopTimer = activity.findViewById(R.id.start_stop_timer_btn);
        resetTimer = activity.findViewById(R.id.reset_timer_btn);
        timerValue = activity.findViewById(R.id.timer_number_txtv);

        startStopTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isStarted){
                    isStarted = false;

                    timeSwapBuff += timeInMilliseconds;

                    customHandler.removeCallbacks(updateTimerThread);
                    startStopTimer.setImageResource(R.drawable.ic_play);

                }else {

                    isStarted = true;

                    startTime = SystemClock.uptimeMillis();

                    customHandler.postDelayed(updateTimerThread, 0);
                    startStopTimer.setImageResource(R.drawable.ic_pause);

                }


            }
        });


        resetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStarted = false;
                timeSwapBuff = 0;
                //customHandler.removeCallbacksAndMessages(null);
                customHandler.removeCallbacks(updateTimerThread);
                timerValue.setText("00:00:00");
                startStopTimer.setImageResource(R.drawable.ic_play);
            }
        });


    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);

            int mins = secs / 60;

            secs = secs % 60;

            int milliseconds = (int) (updatedTime % 1000);

            timerValue.setText(String.format("%02d", mins) + ":"+ String.format("%02d", secs) + ":"+ String.format("%02d", milliseconds));

            customHandler.postDelayed(this, 0);

        }


    };

}
