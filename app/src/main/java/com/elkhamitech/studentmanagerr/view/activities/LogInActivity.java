package com.elkhamitech.studentmanagerr.view.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.elkhamitech.studentmanagerr.businesslogic.accesshandler.AESHelper;
import com.elkhamitech.studentmanagerr.businesslogic.accesshandler.SessionManager;
import com.elkhamitech.studentmanagerr.R;
import com.elkhamitech.studentmanagerr.data.sqlitehelper.DatabaseHelper;
import com.elkhamitech.studentmanagerr.data.model.UserModel;

public class LogInActivity extends AppCompatActivity {

    private ImageView userImg;
    private EditText email;
    private EditText password;
    private Button loginButton;

    private UserModel user;
    private DatabaseHelper db;
    private SessionManager mSessionManager;

    private Intent goTo;

    private long _Id;
    private String emailString;

    //for encryption and decryption
    private String seedValue = "I don't know, what is this?";
    private String normalTextEnc;
    private String normalTextDec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        getSupportActionBar().setTitle("Login");

        userImg = findViewById(R.id.applogo_imgv_login);
        email = findViewById(R.id.email_edtxt_login);
        password = findViewById(R.id.password_edtxt_login);
        loginButton = findViewById(R.id.log_in_btn_submit);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        email.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);

        loginButton.setEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loginButton.setBackgroundTintList(ContextCompat.getColorStateList(LogInActivity.this,android.R.color.darker_gray));
        }

    }

    public void loginSubmit(View view) {

        String authUser = email.getText().toString();
        String authPass = password.getText().toString();



        if(authUser.isEmpty() || authPass.isEmpty()){
           Toast.makeText(this,"Please fill the missing fields",Toast.LENGTH_SHORT).show();
        }else {

            user = new UserModel();
            db = new DatabaseHelper(this);
            mSessionManager = new SessionManager(this);

            emailString = user.getE_mail();

            try {
                normalTextEnc = AESHelper.encrypt(seedValue, authPass);
                normalTextDec = AESHelper.decrypt(seedValue,db.getRegisteredUser(authUser,normalTextEnc).getPassword());
            } catch (Exception e) {
                e.printStackTrace();
            }
            _Id = db.getRegisteredUser(authUser,normalTextEnc).getRow_id();
            if(!authUser.equals(user.getUser_name()) && !authPass.equals(normalTextDec)) {
                Toast.makeText(this, "User name or password are incorrect", Toast.LENGTH_SHORT).show();
            }else {

                mSessionManager.createLoginSession(_Id,emailString);
                goTo = new Intent(LogInActivity.this,MainActivity.class);
                startActivity(goTo);
                finish();
            }

        }



    }

    public void goToSignUp(View view) {
        goTo = new Intent(this,SignUpActivity.class);
        startActivity(goTo);
        finish();
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if(email.getText().toString().length()==0 || password.getText().toString().length()==0){
                loginButton.setEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    loginButton.setBackgroundTintList(ContextCompat.getColorStateList(LogInActivity.this,android.R.color.darker_gray));
                }
            } else {
                loginButton.setEnabled(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    loginButton.setBackgroundTintList(ContextCompat.getColorStateList(LogInActivity.this,R.color.colorAccent));
                }
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

}
