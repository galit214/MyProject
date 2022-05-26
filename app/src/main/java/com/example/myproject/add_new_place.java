package com.example.myproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_new_place extends AppCompatActivity implements View.OnClickListener {
    EditText ed_name,ed_description,ed_address;
    ImageView im_Placeimage;
    Button btn_savePlace;
    DatabaseReference place_ref;
    FirebaseAuth firebaseAuth;
    int SELECT_PICTURE=200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_place);
        ed_name=findViewById(R.id.newP_name);
        ed_description=findViewById(R.id.newP_description);
        ed_address=findViewById(R.id.newP_address);
        im_Placeimage=findViewById(R.id.newP_image);
        btn_savePlace=findViewById(R.id.newP_saveButton);

        firebaseAuth=FirebaseAuth.getInstance();
        place_ref= FirebaseDatabase.getInstance().getReference("Places");

        im_Placeimage.setOnClickListener(this);
        btn_savePlace.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v==im_Placeimage){
            imageChooser();
        }
        if(v==btn_savePlace) {
            if(chekInput())

        }

    }

    void imageChooser(){
        Intent i=new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i,"select picture"),SELECT_PICTURE);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==SELECT_PICTURE){
                Uri selectedImageUri=data.getData();
                if(null !=selectedImageUri){
                    im_Placeimage.setImageURI(selectedImageUri);
                }
            }
        }
    }

    public boolean chekInput(String name,String description,String address){
        boolean noEror=true;
        String message="Error:\n\t";

        if(name.length()==0){
            noEror=false;
            message="Mising name of place";
        }
        if(description.length()==0){
            noEror=false;
            message="Mising description of place";
        }
        if (address.length()==0){
            noEror=false;
            message="Mising address of place";
        }
        return noEror;
    }
}