package com.elkhamitech.studentmanagerr.view.activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.elkhamitech.studentmanagerr.businesslogic.accesshandler.SessionManager;
import com.elkhamitech.studentmanagerr.R;

public class WelcomeActivity extends AppCompatActivity {


    SessionManager session;
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        session = new SessionManager(getApplicationContext());

        splashRunner();


    }


    public void splashRunner(){
		/*
		 * New Handler to start the Menu-Activity and close this Splash-Screen
		 * after some seconds.
		 */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
				/* Create an Intent that will start the Menu-Activity. */
                session.checkLogin();
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}
