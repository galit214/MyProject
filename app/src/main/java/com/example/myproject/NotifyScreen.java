package com.example.myproject;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class NotifyScreen extends AppCompatActivity {
    private EditText et_title, et_message;
    private Button btn_send;
    String token;
    DatabaseReference tokenRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_screen);

        //device id
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        token = Data.token;
                        //saveTokenToFB(token);
                        System.out.println("Token " + token);   //welcome to 2019 :)

                    }
                });


        //device 1 to device 2
        et_message = findViewById(R.id.et_message);
        et_title = findViewById(R.id.et_title);
        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = et_title.getText().toString().trim();
                String message = et_message.getText().toString().trim();
                if (!title.equals("") && !message.equals("")) {
                    FCMSend.pushNotifcation(
                            NotifyScreen.this,
                            "edHmQk1uSRGlYFTwr-E7-c:APA91bE2p50ZvvnKtqNA-KbrAECL5FSzfI_vR0T2BQfr_bDFACBeuu-QXWacmIBPGXwsSPhQ_rwmChG18WSdZpZnfTGe2qU6Zjjg2IsYfTN65QZhCGKu-fKCd816NzigDVuntkJszRMH",
                            title,
                            message
                    );
                }
            }
        });

    }

    public void saveTokenToFB(String token){
        String uid = FirebaseAuth.getInstance().getUid();

        User u = new User(uid, token,"","",""); //todo - correct this line

        tokenRef = FirebaseDatabase.getInstance().getReference("Tokens");
        tokenRef.child(uid).setValue(u);
    }


}