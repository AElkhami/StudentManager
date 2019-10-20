package com.elkhamitech.studentmanagerr.view.activities;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elkhamitech.studentmanagerr.R;
import com.elkhamitech.studentmanagerr.businesslogic.accesshandler.AESHelper;
import com.elkhamitech.studentmanagerr.businesslogic.accesshandler.SessionManager;
import com.elkhamitech.studentmanagerr.data.model.UserModel;
import com.elkhamitech.studentmanagerr.data.sqlitehelper.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class EditProfileActivity extends AppCompatActivity {


    private EditText userName;
    private EditText email;
    private EditText password;
    private EditText age;

    private DatabaseHelper db;
    private UserModel user;
    private SessionManager mSessionManager;

    private HashMap<String, Long> rid;
    private long row_id;

    //for encryption and decryption
    private String seedValue = "fake seed not on the store one";
    private String normalTextDec;
    private String normalTextEnc;

    private String authPass;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        userName = findViewById(R.id.user_name_edtxt_edit);
        email = findViewById(R.id.email_edtxt_edit);
        password = findViewById(R.id.password_edtxt_edit);
        age = findViewById(R.id.age_edtxt_edit);

        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBirthDate();
            }
        });

        getUserDetails();
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        age.setText(sdf.format(myCalendar.getTime()));
    }

    private void getUserDetails() {

        db = new DatabaseHelper(getApplicationContext());
        user = new UserModel();
        mSessionManager = new SessionManager(this);

        rid= mSessionManager.getRowDetails();

        row_id = rid.get(SessionManager.KEY_ID);

        user = db.getLoggedUser(row_id);


        try {
            normalTextDec = AESHelper.decrypt(seedValue,user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }

        userName.setText(user.getUser_name());
        email.setText(user.getE_mail());
        password.setText(normalTextDec);
        age.setText(user.getAge());
    }

    public void getBirthDate(){

        new DatePickerDialog(EditProfileActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    public void updateProfile(){

        authPass = password.getText().toString();

        try {
            normalTextEnc = AESHelper.encrypt(seedValue, authPass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        user.setUser_name(userName.getText().toString());
        user.setPassword(normalTextEnc);
        user.setE_mail(email.getText().toString());
        user.setAge(age.getText().toString());

        db.updateUser(user);
        Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
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
                updateProfile();
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
