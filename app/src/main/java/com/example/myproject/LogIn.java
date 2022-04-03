package com.example.myproject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth firebaseAuth;
    EditText ed_email,ed_password;
    Button btn_logIn;
    TextView tv_forgetPass,tv_goSignUp;
    LinearLayout layout_login;
    Dialog d;
    EditText et_email_dialog; //dialog buttons
    Button  btn_reset_dialog,btn_back_dialog;
    ProgressDialog progressDialog;
    String message;
    DatabaseReference user_ref;
    ArrayList<User> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        layout_login=findViewById(R.id.layout_logIn);
        ed_email=findViewById(R.id.ed_email_Log);
        ed_password=findViewById(R.id.ed_passwordLog);
        btn_logIn=findViewById(R.id.btn_logIn);
        tv_forgetPass=findViewById(R.id.tv_forgetPass);
        btn_logIn.setOnClickListener(this);
        tv_forgetPass.setOnClickListener(this);
        tv_goSignUp=findViewById(R.id.tv_goSignUP);
        tv_goSignUp.setOnClickListener(this);


        firebaseAuth=FirebaseAuth.getInstance();
        user_ref= FirebaseDatabase.getInstance().getReference("Users");



    }



    @Override
    public void onClick(View view) {
        if(view==tv_forgetPass){
            createForgetrPassDialog();
        }
        if(view==btn_back_dialog){
            d.dismiss();
        }
        if (view == btn_reset_dialog) {
            String email=et_email_dialog.getText().toString();
            if(email.trim().length()!=0){
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mySnackBar("email successful");
                        }
                        else{
                            mySnackBar(task.getException().getMessage());
                        }
                    }
                });
            }
            else{
                d.dismiss();
               mySnackBar("please enter the email");

            }


        }
        if(view==btn_logIn){
            String email=ed_email.getText().toString();
            String pass=ed_password.getText().toString();
            if(!checkInput(email,pass)) {
             firebaseAuth.createUserWithEmailAndPassword(
                        email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      if (task.isSuccessful()) {
                          message="Successfully registered";
                          mySnackBar(message);

                       }
                      else {
                         Toast.makeText(LogIn.this,
                         "Registration Error!",
                          Toast.LENGTH_SHORT).show();
                      }

                     }
                });
            }
            login(ed_password.getText().toString(),ed_email.getText().toString());

        }
        if(view==tv_goSignUp){
            Intent intent=new Intent(LogIn.this,signUp.class);
            startActivity(intent);
        }
    }




    public void createForgetrPassDialog(){
        d=new Dialog(this);
        d.setContentView(R.layout.reset_password_dialog);
        d.setTitle("Reset Password");
        d.setCancelable(true);
        et_email_dialog=d.findViewById(R.id.ed_email_dialog);
        btn_back_dialog=d.findViewById(R.id.btn_back_dialog);
        btn_reset_dialog=d.findViewById(R.id.btn_reset_dialog);
        btn_back_dialog.setOnClickListener(this);
        btn_reset_dialog.setOnClickListener(this);


        d.show();




    }

    public boolean checkInput(String email, String pass){
        boolean error=false;
        String message="Error:\n\t";
        if(email.trim().length()==0){
            error=true;
            message+= "Enter your registed email.\t";
        }
        if(pass.trim().length()==0){
            error=true;
            message+= "Missing required password.\t";
        }
        if(error){
            mySnackBar(message);
        }
        return error;

    }
    public void mySnackBar(String message){
        Snackbar snackbar=Snackbar.make(layout_login,message,Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void login(String password, String email){

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent=new Intent(LogIn.this,student_main.class);
                    startActivity(intent);
                }
                else{
                    mySnackBar("your password or email not correct");
                }

            }
        });
    }


}