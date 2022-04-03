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
                checkInput(ed_date.getText().toString(), ed_hours.getText().toString(), ed_description.getText().toString());
            }
        });
        return view;
    }




    public boolean checkInput(String date, String hours,String description){
        boolean error=false;
        String message = "Error:\n\t";
        if(date.trim().length()==0){
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