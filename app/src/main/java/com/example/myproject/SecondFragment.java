package com.example.myproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class SecondFragment extends Fragment {
    EditText ed_date,ed_hours,ed_description;
    Spinner spinner;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    String date;
    Button btn_aproove_h;
    private static final String LOG_TAG = "LogActivity";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mehoivaotRef;
    FirebaseAuth firebaseAuth;



    public SecondFragment(){
        // require a empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        ed_date = view.findViewById(R.id.ed_date_addH);
        ed_hours = view.findViewById(R.id.ed_num_hours_addH);
        ed_description = view.findViewById(R.id.ed_description_addH);
        btn_aproove_h = view.findViewById(R.id.btn_send_confirm_addH);
        firebaseDatabase=FirebaseDatabase.getInstance();
        mehoivaotRef=firebaseDatabase.getReference("Mehoiavot").push();



        //current date
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
        ed_date.setText(date);


        ArrayList<String> spinner_list = new ArrayList<String>();
        spinner_list.add("place");
        spinner_list.add("second place");
        spinner_list.add("third place");
        spinner_list.add("fourth place");

        spinner = view.findViewById(R.id.spinner);


        spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinner_list));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String place = adapterView.getItemAtPosition(position).toString();
                Log.d(LOG_TAG, "You selected " + place);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_aproove_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkInput(ed_date.getText().toString(),ed_hours.getText().toString(),ed_description.getText().toString())){
                    FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser() ;
                    String userId=currentUser.getUid();
                    double hours=Double.parseDouble(ed_hours.getText().toString());
                    Mehoiavot m=new Mehoiavot(hours,ed_description.getText().toString(),
                              "",ed_date.getText().toString(),false,userId);
                    mehoivaotRef.setValue(m);
                }

            }
        });
        return view;
    }




    public boolean checkInput(String date, String hours, String description){
        boolean error=false;
        String message = "Error:\n\t";
        if(date==null){
            error=true;
            message="enter date.\t";
        }
        if(hours.trim().length()==0){
            error=true;
            message="enter number of hours.\t";
        }
        if(description.trim().length()==0){
            error=true;
            message="enter description.\t";
        }
        if(error){
            mySnackBar(message);
        }

        return error;


    }

        public void mySnackBar(String message) {
        Snackbar snackbar = Snackbar.make(requireActivity().findViewById(R.id.secondFragment), message, Snackbar.LENGTH_LONG);
        snackbar.show();

    }

}