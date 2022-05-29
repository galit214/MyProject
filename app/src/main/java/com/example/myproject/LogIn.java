package com.example.myproject;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth firebaseAuth;
    User user;
    EditText ed_email, ed_password;
    Button btn_logIn;
    TextView tv_forgetPass, tv_goSignUp;
    LinearLayout layout_login;
    Dialog d;
    EditText et_email_dialog; //dialog buttons
    Button btn_reset_dialog, btn_back_dialog;
    DatabaseReference user_ref;
    CheckBox cb_rme;
    ArrayList<User> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        layout_login = findViewById(R.id.layout_logIn);
        ed_email = findViewById(R.id.ed_email_Log);
        ed_password = findViewById(R.id.ed_passwordLog);
        btn_logIn = findViewById(R.id.btn_logIn);
        tv_forgetPass = findViewById(R.id.tv_forgetPass);
        btn_logIn.setOnClickListener(this);
        tv_forgetPass.setOnClickListener(this);
        tv_goSignUp = findViewById(R.id.tv_goSignUP);
        tv_goSignUp.setOnClickListener(this);
        cb_rme = findViewById(R.id.cb_rme);
        firebaseAuth = FirebaseAuth.getInstance();
        user_ref = FirebaseDatabase.getInstance().getReference("Users");


        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        if (checkbox.equals("true")) {
            //todo - check if user is registered
            isConnected();
            Intent intent = new Intent(LogIn.this, student_main.class);
            //startActivity(intent);
        }


        cb_rme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Checked", Toast.LENGTH_SHORT).show();


                } else if (!compoundButton.isChecked()) {
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "UnChecked", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    public void isConnected() {
        if (firebaseAuth != null) {
            String email = ed_email.getText().toString();
            String pass = ed_password.getText().toString();
            if (checkInput(email, pass)) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            (Toast.makeText(getApplicationContext(), "login successful",
                                    Toast.LENGTH_LONG)).show();
                            getData();
                            /*Intent intent1=new Intent(LogIn.this,student_main.class);
                            startActivity(intent1);*/
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Login failed!!",
                                    Toast.LENGTH_LONG)
                                    .show();

                        }
                    }
                });
            }
        }
    }

    private void getData() {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String uid = firebaseUser.getUid();
        user_ref = FirebaseDatabase.getInstance().getReference("Users/" + uid);
        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user  = snapshot.getValue(User.class);
                Log.d("tag", user.getName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }

    @Override
    public void onClick(View view) {
        if (view == tv_forgetPass) {
            createForgetrPassDialog();
        }
        if (view == btn_back_dialog) {
            d.dismiss();
        }
        if (view == btn_reset_dialog) {
            String email = et_email_dialog.getText().toString();
            if (email.trim().length() != 0) {
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mySnackBar("email successful");
                        } else {
                            mySnackBar(task.getException().getMessage());
                        }
                    }
                });
            } else {
                d.dismiss();
                mySnackBar("please enter the email");

            }


        }
        if (view == btn_logIn) {
            isConnected();

        }
        if (view == tv_goSignUp) {
            Intent intent = new Intent(LogIn.this, signUp.class);
            startActivity(intent);
        }

    }


    public void createForgetrPassDialog() {
        d = new Dialog(this);
        d.setContentView(R.layout.reset_password_dialog);
        d.setTitle("Reset Password");
        d.setCancelable(true);
        et_email_dialog = d.findViewById(R.id.ed_email_dialog);
        btn_back_dialog = d.findViewById(R.id.btn_back_dialog);
        btn_reset_dialog = d.findViewById(R.id.btn_reset_dialog);
        btn_back_dialog.setOnClickListener(this);
        btn_reset_dialog.setOnClickListener(this);


        d.show();


    }

    public boolean checkInput(String email, String pass) {
        boolean noerror = true;
        String message = "Error:\n\t";
        if (email.length() == 0) {
            noerror = false;
            message += "Enter your registed email.\t";
        }
        if (pass.length() == 0) {
            noerror = false;
            message += "Missing required password.\t";
        }
        if (!noerror) {
            mySnackBar(message);
        }
        return noerror;

    }


    public void mySnackBar(String message) {
        Snackbar snackbar = Snackbar.make(layout_login, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }


}