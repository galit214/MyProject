package com.example.myproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MehoiavotAdapter extends ArrayAdapter<Mehoiavot> {
    public MehoiavotAdapter(@NonNull Context context, ArrayList<Mehoiavot> mehoiavotArrayList) {
        super(context, 0, mehoiavotArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView=convertView;
        if(listitemView==null){
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView=LayoutInflater.from(getContext()).inflate(R.layout.time_item,parent,false);
        }
        Mehoiavot mehoiavot=getItem(position);
        TextView dateTV=listitemView.findViewById(R.id.tv_date_gv);
        TextView aprovedTV=listitemView.findViewById(R.id.tv_approved_gv);
        dateTV.setText(mehoiavot.getDate());
        aprovedTV.setText(mehoiavot.getApproved().toString());
        return listitemView;
    }
}

