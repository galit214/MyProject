package com.example.myproject;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;

public class Data {
    public static String token="";
    //token1 = eemqM0zjRXiVMFj1yLQhzf:APA91bGNID369raMMnDHvpMuUATGQqOYVEv3KuI2D44_ID9YIBXUqhUmSpo-s6vTyEoSvnJ8gNOK3DoH-0u0R9YAmpJ86bMlkQGddsqMTkFfUaiMC4kmG957hA_tWAULZdSVF9SY8gz_
    //token2 = eQj9zLRfQaOpGMwKLX90qF:APA91bFOCoEUWHLwk_JOdIYVOnKeIZlEnGYft3T76xAaITedTwsOyGpKc1Qcj-Vbuy1G4iGrfOdXXhmDc2X-GF6aZ10HA7QQ4EbNcs__Y46RPuKNsPilIhbTK_ILSzD5_iiqwysF-l6B

    public static void collectTokenDevice(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();
                        //saveTokenToFB(token);
                        System.out.println("Token " + token);

                    }
                });
    }
}
