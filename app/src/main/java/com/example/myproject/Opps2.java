package com.example.myproject;

import java.io.IOException;
import java.util.List;
import com.google.android.gms.maps.model.LatLng;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Opps2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opps2);

        //String address= "wizo canada school nahalal";
        //openAddressOnMap(address);

        //add place to firebase
        addPlace();
    }

    private void addPlace() {
        //public Place(String name, String description, Image image, String addres, String pid)
    }


    private void openAddressOnMap(String address) {
        Intent searchAddress = new  Intent(Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q="+address));
        startActivity(searchAddress);
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return p1;
    }
}