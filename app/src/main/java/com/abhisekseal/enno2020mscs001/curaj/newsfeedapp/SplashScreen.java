package com.abhisekseal.enno2020mscs001.curaj.newsfeedapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class SplashScreen extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser mCurrentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth=FirebaseAuth.getInstance();
        mCurrentUser=mAuth.getCurrentUser();

        if (mCurrentUser!=null){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 1000);


        }
        else{

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent=new Intent(getApplicationContext(),LogInRegister.class);
                    startActivity(intent);
                    finish();
                }
            }, 1000);
        }
    }
}