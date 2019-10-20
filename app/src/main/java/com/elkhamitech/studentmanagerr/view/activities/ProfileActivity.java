package com.elkhamitech.studentmanagerr.view.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.elkhamitech.studentmanagerr.businesslogic.accesshandler.SessionManager;
import com.elkhamitech.studentmanagerr.R;
import com.elkhamitech.studentmanagerr.data.sqlitehelper.DatabaseHelper;
import com.elkhamitech.studentmanagerr.data.model.UserModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    private ImageView userImg;
    private TextView userName;

    private DatabaseHelper db;
    private UserModel user;
    private SessionManager mSessionManager;

    private LinearLayout hoverLinear;
    private ImageButton hoverEdit;


    private HashMap<String, Long> rid;
    private long row_id;

    private Intent goTo;

    private final int REQUEST_CODE_GALLERY = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("Profile");

        userImg = findViewById(R.id.user_img_view);
        userName = findViewById(R.id.user_name_textv);

        hoverLinear = findViewById(R.id.hover_campus);
        hoverEdit = findViewById(R.id.hover_button);

        db = new DatabaseHelper(getApplicationContext());
        user = new UserModel();
        mSessionManager = new SessionManager(this);

        rid= mSessionManager.getRowDetails();

        row_id = rid.get(SessionManager.KEY_ID);

        user = db.getLoggedUser(row_id);

        userName.setText(user.getUser_name().toString());


        Glide.with(ProfileActivity.this)
                .load(user.getUserImage()) // add your image url
                .apply(RequestOptions.circleCropTransform()) // applying the image transformer
                .into(userImg);

        hoverLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserImage();
            }
        });

    }

    public void logOut(View view) {

        mSessionManager.logoutUser();
        finish();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }

    }

    public void goToAbout(View view) {
        goTo = new Intent(this, HelpActivity.class);
        startActivity(goTo);
    }

    public void updateProfile(View view) {

        goTo = new Intent(this, EditProfileActivity.class);
        startActivity(goTo);
    }

    public void updateUserImage(){

        ActivityCompat.requestPermissions(
                ProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);

    }


    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
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


                Glide.with(this)
                        .load(uri)
                        .apply(RequestOptions.circleCropTransform())
                        .into(userImg);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    user.setUserImage(imageViewToByte(userImg));
                    db.updateUserPp(user);
                }
            }, 2000);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
