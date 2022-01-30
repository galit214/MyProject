package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class student_main extends AppCompatActivity {
    Button btn_addMehoivaot;
    DatabaseReference mehoivaotRef;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        firebaseDatabase = FirebaseDatabase.getInstance();


        btn_addMehoivaot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mehoiavot m=new mehoiavot();
                mehoivaotRef=firebaseDatabase.getReference("mehoiavots").push();
                mehoivaotRef.setValue(m);


            }
        });
    }
}