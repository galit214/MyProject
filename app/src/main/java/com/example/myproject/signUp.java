package com.example.myproject;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

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
    ActivityResultLauncher<String> result_content;
    ImageView user_img;
    Uri uri_image;
    StorageReference storageRef;
    private static final int PICK_IMAGE_REQUEST=1;
    StorageTask upload_task;
    ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        storageRef= FirebaseStorage.getInstance().getReference("images");
        userRef = firebaseDatabase.getReference("Users").push();



        result_content= registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        user_img.setImageURI(result);
                        uri_image=result;
                        Picasso.get().load(uri_image).into(user_img);
                    }
                });


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
        progressBar=findViewById(R.id.progress_bar_signUp);
        user_img=findViewById(R.id.user_image);


        btn_signUp.setOnClickListener(this);
        img_back.setOnClickListener(this);
        im_see_p.setOnClickListener(this);
        im_see_cop.setOnClickListener(this);
        user_img.setOnClickListener(this);

        et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        et_co_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            uri_image = data.getData();
            //upload image to firebase using picasso
            Picasso.get().load(uri_image).into(user_img); //replace with to get
        }
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
                        et_password.getText().toString(), radioButton.getText().toString(), "",null);
                u.setUid(userRef.getKey());
                uploadFile();
                u.setImageUrl(uri_image.toString());
                userRef.setValue(u);



            }
            if(radioButton.getText().equals("תלמיד")){
                Intent intent=new Intent(signUp.this,student_main.class);
                startActivity(intent);
            }
            if(radioButton.getText().equals("עובד מתנדב")){
                Intent intent =new Intent(signUp.this,add_new_place.class);
                startActivity(intent);
            }
        }

        if(view==user_img){
            result_content.launch("image/*");


        }
    }

    private void createNewUser() {
        firebaseAuth.createUserWithEmailAndPassword(
                et_email.getText().toString(),
                et_password.getText().toString()).
                addOnCompleteListener(signUp.this, new OnCompleteListener<AuthResult>() {
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
        String message = "Error";
        if (email.trim().length() == 0) {
            error = true;
            message = "Enter your registed email";
        }
        if (pass.trim().length() == 0) {
            error = true;
            message = "Missing required password";
        }
        if (name.trim().length() == 0) {
            error = true;
            message = "Enter your name";
        }
        if (id.trim().length() == 0) {
            error = true;
            message = "enter id";
        }
        if (coPassword.trim().length() == 0) {
            error = true;
            message = "confirm your password";
        }
        if (!coPassword.trim().equals(pass.trim())) {
            error = true;
            message = "your password doesnt match";
        }
        if (error) {
            mySnackBar(message);
        }

        return error;

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();  //singleton - https://techvidvan.com/tutorials/java-singleton-class/

        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadFile() {
        if (uri_image != null) {
            StorageReference fileRef = storageRef.child(
                    System.currentTimeMillis() + "." + getFileExtension(uri_image));
            upload_task = fileRef.putFile(uri_image).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(signUp.this,
                                    "Upload Successfully!", Toast.LENGTH_SHORT).show();
                            //below code will create a bug (wont show images)- fix is below
                                /*Upload upload = new Upload(type_selected,
                                        taskSnapshot.getMetadata().
                                                getReference().getDownloadUrl().toString());
                                String upLoadId = imageRef.push().getKey();
                                imageRef.child(upLoadId).setValue(upload);*/
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful()) ;
                            Uri downloadUrl = urlTask.getResult();

                            /*String imageUrl=uri_image.toString();

                            String uploadId = userRef.push().getKey();
                            userRef.child("imageUrl").setValue(imageUrl)*/

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(signUp.this,
                            e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(
                    new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 *
                                    snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });

        }

    }
    public void mySnackBar(String message){
        Snackbar snackbar = Snackbar.make(layOut_signUp, message, Snackbar.LENGTH_LONG);
        snackbar.show();

    }
}
