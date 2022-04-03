package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signUp extends AppCompatActivity implements View.OnClickListener {
    EditText et_name, et_email, et_password, et_co_password, et_id;
    ImageView img_back;
    ImageButton im_see_p,im_see_cop;
    Button btn_signUp;
    RadioGroup rg_type;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userRef;
    FirebaseAuth firebaseAuth;
    LinearLayout layOut_signUp;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_sign_up);


        rg_type = findViewById(R.id.rg_type);
        et_id = findViewById(R.id.ed_id);
        et_name = findViewById(R.id.ed_name);
        et_email = findViewById(R.id.ed_email);
        et_password = findViewById(R.id.ed_password);
        et_co_password = findViewById(R.id.ed_co_password);
        img_back = findViewById(R.id.img_back);
        btn_signUp = findViewById(R.id.btn_signUp);
        layOut_signUp = findViewById(R.id.layout_signUp);
        im_see_p=findViewById(R.id.Ib_see_p);
        im_see_cop=findViewById(R.id.Ib_see_coP);


        btn_signUp.setOnClickListener(this);
        img_back.setOnClickListener(this);
        im_see_p.setOnClickListener(this);
        im_see_cop.setOnClickListener(this);

        et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        et_co_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

    }

    @Override
    public void onClick(View view) {
        if (view == img_back) {
            Intent intent = new Intent(signUp.this, LogIn.class);
            startActivity(intent);
        }
        if(view==im_see_p){

        }


        if (view == btn_signUp) {
            int selectedId=rg_type.getCheckedRadioButtonId();
            radioButton =findViewById(selectedId);

            if (!checkInput(et_email.getText().toString(), et_password.getText().toString(),
                   et_name.getText().toString(), et_id.getText().toString(), et_co_password.getText().toString())) {
                createNewUser();
                User u = new User(et_name.getText().toString(), et_email.getText().toString(),
                        et_password.getText().toString(), radioButton.getText().toString(), "");
                userRef = firebaseDatabase.getReference("Users").push();
                u.setUid(userRef.getKey());
                userRef.setValue(u);

            }
            if(radioButton.getText().equals("תלמיד")){
                Intent intent=new Intent(signUp.this,student_main.class);
                startActivity(intent);
            }
        }
    }

    private void createNewUser() {
        firebaseAuth.createUserWithEmailAndPassword(
                et_email.getText().toString(),
                et_password.getText().toString()).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(signUp.this,
                                    "Successfully registered", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(signUp.this,
                                    "Registration Error!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }

    public boolean checkInput(String email, String pass, String name, String id, String coPassword) {
        boolean error = false;
        String message = "Error:\n\t";
        if (email.trim().length() == 0) {
            error = true;
            message = "Enter your registed email.\t";
        }
        if (pass.trim().length() == 0) {
            error = true;
            message = "Missing required password.\t";
        }
        if (name.trim().length() == 0) {
            error = true;
            message = "Enter your name.\t";
        }
        if (id.trim().length() == 0) {
            error = true;
            message = "enter id.\t";
        }
        if (coPassword.trim().length() == 0) {
            error = true;
            message = "confirm your password.\t";
        }
        if (!coPassword.trim().equals(pass.trim())) {
            error = true;
            message = "your password doesnt match.\t";
        }
        if (error) {
            mySnackBar(message);
        }

        return error;

    }


    public void mySnackBar(String message) {
        Snackbar snackbar = Snackbar.make(layOut_signUp, message, Snackbar.LENGTH_LONG);
        snackbar.show();

    }


}
