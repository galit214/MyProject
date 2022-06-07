package com.example.myproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_new_place extends AppCompatActivity implements View.OnClickListener {
    EditText ed_name,ed_description,ed_address;
    ImageView im_Place;
    Button btn_savePlace;
    DatabaseReference place_ref;
    FirebaseDatabase firebaseDatabase;
    int SELECT_PICTURE=200;
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_place);
        ed_name=findViewById(R.id.newP_name);
        ed_description=findViewById(R.id.newP_description);
        ed_address=findViewById(R.id.newP_address);
        im_Place =findViewById(R.id.newP_image);
        btn_savePlace=findViewById(R.id.newP_saveButton);

        firebaseDatabase=FirebaseDatabase.getInstance();
        place_ref= FirebaseDatabase.getInstance().getReference("Places/");

        im_Place.setOnClickListener(this);
        btn_savePlace.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v== im_Place){
            imageChooser();
        }
        if(v==btn_savePlace) {
            String name=ed_name.getText().toString();
            String description=ed_description.getText().toString();
            String address=ed_address.getText().toString();
            if(chekInput(name,description,address)){
                Place p=new Place(name,description,null,address,null);
                place_ref= firebaseDatabase.getReference("places").push();
                p.setPid(place_ref.getKey());
                place_ref.setValue(p);

            }

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
                    im_Place.setImageURI(selectedImageUri);
                    imageUri=selectedImageUri;
                }
            }
        }
    }

    public boolean chekInput(String name,String description,String address){
        boolean noEror=true;

        if(name.length()==0){
            noEror=false;
        }
        if(description.length()==0){
            noEror=false;
        }
        if (address.length()==0){
            noEror=false;
        }
        return noEror;
    }

}