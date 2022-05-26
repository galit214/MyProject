package com.example.myproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class welcome extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton fav_settings;
    LinearLayout welcome_layout;
    Button btn_logIn, btn_signUp;
    int countClicks = 0;
    String adminId = "nkmAv73sv6Xsr38URuHwufgRYL93";
    String uid="";


    // todo- (3)  שמירת מסד הנתונים חסרה

    //todo- (4) check screen flow


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        welcome_layout = findViewById(R.id.welcome_layout);

        uid = FirebaseAuth.getInstance().getUid();
        setContentView(R.layout.activity_welcome);

        btn_logIn=findViewById(R.id.btn_logIn);
        btn_signUp=findViewById(R.id.btn_SignUp);
        btn_signUp.setOnClickListener(this);
        btn_logIn.setOnClickListener(this);
        fav_settings = findViewById(R.id.fav_settings);
        fav_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countClicks++;
                if(countClicks==7 && uid.equals(adminId)){

                    Snackbar snackbar = Snackbar
                            .make(welcome_layout, "hola admin boss!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        if (view == btn_logIn){
            Intent intent=new Intent(welcome.this,LogIn.class);
            startActivity(intent);
        }
        if (view == btn_signUp){
            Intent intent=new Intent(welcome.this,signUp.class);
            startActivity(intent);

        }

    }

}