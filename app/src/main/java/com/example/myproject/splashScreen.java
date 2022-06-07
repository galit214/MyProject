package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
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
                Log.d("token", "the token device: "+Data.token);

                Intent intent=new Intent(splashScreen.this, NotifyScreen.class);
                startActivity(intent);
                finish();
            }
        },1000);

        Data.collectTokenDevice();
    }

    private void addPlaces(){

    }
}