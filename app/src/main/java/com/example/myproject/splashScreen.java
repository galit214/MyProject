package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

public class splashScreen extends AppCompatActivity {
    Handler handler;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        firebaseDatabase=FirebaseDatabase.getInstance();
        addPlaces();
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(splashScreen.this, LogIn.class);
                startActivity(intent);
                finish();
            }
        },1000);

    }

    private void addPlaces(){

    }
}