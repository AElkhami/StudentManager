package com.elkhamitech.studentmanagerr.view.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.elkhamitech.studentmanagerr.businesslogic.accesshandler.AESHelper;
import com.elkhamitech.studentmanagerr.businesslogic.accesshandler.SessionManager;
import com.elkhamitech.studentmanagerr.R;
import com.elkhamitech.studentmanagerr.data.sqlitehelper.DatabaseHelper;
import com.elkhamitech.studentmanagerr.data.model.UserModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {

    private ImageView userImage;
    private EditText userName;
    private EditText password;
    private EditText age;
    private EditText email;
    private Button signupSubmitBtn;

    private SessionManager mSessionManager;

    private long _Id;
    private String emailString;

    private UserModel user;
    private Intent goTo;
    private DatabaseHelper db;

    private final int REQUEST_CODE_GALLERY = 999;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    //for encryption and decryption
    private String seedValue = "fake seed not on the store one";
    private String normalTextEnc;
    private String normalTextDec;

    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setTitle("Sign up");

        userImage = findViewById(R.id.user_img_imgv_signup);
        userName = findViewById(R.id.user_name_edtxt_signup);
        password = findViewById(R.id.password_edtxt_signup);
        age = findViewById(R.id.age_edtxt_signup);
        email = findViewById(R.id.email_edtxt_signup);
        signupSubmitBtn = findViewById(R.id.signup_submit_btn);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        userName.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        email.addTextChangedListener(emailWatcher);
        email.addTextChangedListener(textWatcher);
        age.addTextChangedListener(textWatcher);

        signupSubmitBtn.setEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            signupSubmitBtn.setBackgroundTintList(ContextCompat.getColorStateList(SignUpActivity.this,android.R.color.darker_gray));
        }

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

    }

    public void signupSubmit(View view) {

        String authUser = email.getText().toString();
        String authPass = password.getText().toString();
        mSessionManager = new SessionManager(this);

        if(userName.getText().toString().isEmpty() || password.getText().toString().isEmpty()
                || age.getText().toString().isEmpty() || email.getText().toString().isEmpty()){
            Toast.makeText(this,"Please fill the missing fields.",Toast.LENGTH_SHORT).show();
        }else {

            if(!(authPass.length()<6)){
                user = new UserModel();
                db = new DatabaseHelper(getApplicationContext());

                try {
                    normalTextEnc = AESHelper.encrypt(seedValue, authPass);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                user.setUser_name(userName.getText().toString());
                user.setPassword(normalTextEnc);
                user.setAge(age.getText().toString());
                user.setE_mail(email.getText().toString());
                user.setUserImage(imageViewToByte(userImage));

                db.createUser(user);

                emailString = user.getE_mail();

                _Id = db.getRegisteredUser(authUser,normalTextEnc).getRow_id();

                mSessionManager.createLoginSession(_Id,emailString);
                goTo = new Intent(this,MainActivity.class);
                startActivity(goTo);
                finish();
            }else {
                Toast.makeText(this, "Your password shoun't be less than 6 characters", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        age.setText(sdf.format(myCalendar.getTime()));
    }

    public void getBirthDate(){

        new DatePickerDialog(SignUpActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }


    public void goToLogin(View view) {

        goTo = new Intent(this,LogInActivity.class);
        startActivity(goTo);
        finish();
    }

    public void selectImage(View view) {

        ActivityCompat.requestPermissions(
                SignUpActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
    }


    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

//            try {
//                InputStream inputStream = getContentResolver().openInputStream(uri);
//
//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                Glide.with(this)
                        .load(uri)
                        .apply(RequestOptions.circleCropTransform())
                        .into(userImage);

//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if(userName.getText().toString().length()==0 ||
                    email.getText().toString().length()==0 ||
                    password.getText().toString().length()==0 ||
                    age.getText().toString().length()==0){

                signupSubmitBtn.setEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    signupSubmitBtn.setBackgroundTintList(ContextCompat.getColorStateList(SignUpActivity.this,android.R.color.darker_gray));
                }
            } else {
                signupSubmitBtn.setEnabled(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    signupSubmitBtn.setBackgroundTintList(ContextCompat.getColorStateList(SignUpActivity.this,R.color.colorAccent));
                }
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    TextWatcher emailWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            emailString = email.getText().toString();

            if (!emailString.matches(emailPattern) && charSequence.length() > 0) {
                email.setError("Invalid email address");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
