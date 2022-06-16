package com.example.myproject;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class add_new_place extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST=1;
    EditText ed_name,ed_description,ed_address,ed_phone;
    ImageView im_Place;
    Button btn_savePlace;
    DatabaseReference place_ref;
    FirebaseDatabase firebaseDatabase;
    StorageReference storageRef;
    StorageTask upload_task;
    Uri uri_image;
    ActivityResultLauncher<String> result_content;
    ProgressBar progressBar;
    LinearLayout layout_add_new_place;

    int SELECT_PICTURE=200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_place);


        ed_name=findViewById(R.id.newP_name);
        ed_description=findViewById(R.id.newP_description);
        ed_address=findViewById(R.id.newP_address);
        ed_phone=findViewById(R.id.newP_phone);
        im_Place =findViewById(R.id.newP_image);
        btn_savePlace=findViewById(R.id.newP_saveButton);
        progressBar=findViewById(R.id.progress_bar);
        layout_add_new_place=findViewById(R.id.layout_add_new_place);

        firebaseDatabase=FirebaseDatabase.getInstance();
        place_ref=firebaseDatabase.getReference("Place").push();
        storageRef= FirebaseStorage.getInstance().getReference("images");

        im_Place.setOnClickListener(this);
        btn_savePlace.setOnClickListener(this);

        result_content= registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        im_Place.setImageURI(result);
                        uri_image=result;
                        Picasso.get().load(uri_image).into(im_Place);
                    }
                });


    }

    private void openFileChooser(){
        result_content.launch("image/*");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            uri_image = data.getData();
            //upload image to firebase using picasso
            Picasso.get().load(uri_image).into(im_Place); //replace with to get
        }
    }


    @Override
    public void onClick(View v) {
        if(v== im_Place){
            openFileChooser();
        }
        if(v==btn_savePlace) {
            String name=ed_name.getText().toString();
            String description=ed_description.getText().toString();
            String address=ed_address.getText().toString();
            String phone=ed_phone.getText().toString();
            if(chekInput(name,description,address,phone)){
                Place p=new Place(name,description,null,address,null,phone,null);
                uploadFile();
                p.setImageUrl(uri_image.toString());
                place_ref.setValue(p);

            }

        }

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
                                    public void run() {progressBar.setProgress(0);
                                    }
                                }, 500);
                                Toast.makeText(add_new_place.this,
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


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(add_new_place.this,
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









    public boolean chekInput(String name,String description,String address,String phone){
        boolean noEror=true;
        String message="Error:\n\t";

        if(name.length()==0){
            noEror=false;
            message="Enter place name.\t";
        }
        if(description.length()==0){
            noEror=false;
            message="Enter place description.\t";

        }
        if (address.length()==0){
            noEror=false;
            message="Enter place address.\t";

        }
        if(phone.length()==0){
            noEror=false;
            message="Enter menager phone number.\t";

        }
        if(!noEror){
            mySnakeBar(message);

        }
        return noEror;
    }

    public void mySnakeBar(String message) {
        Snackbar snackbar = Snackbar.make(layout_add_new_place, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}