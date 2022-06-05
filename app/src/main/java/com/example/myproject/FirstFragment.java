package com.example.myproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FirstFragment extends Fragment {
    GridView allTimeGv;
    ArrayList<Mehoiavot> mehoiavotArrayList;
    public FirstFragment(){
        // require a empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        allTimeGv = view.findViewById(R.id.gv_allTime);
        mehoiavotArrayList = new ArrayList<Mehoiavot>();
        mehoiavotArrayList.add(new Mehoiavot(2.5, "something", "null",
                "2;27;2000", false));
        mehoiavotArrayList.add(new Mehoiavot(3, "something", "null",
                "2/27/2000", true));
        mehoiavotArrayList.add(new Mehoiavot(2.5, "something", "null",
                "2/27/2000", false));
        mehoiavotArrayList.add(new Mehoiavot(4, "something", "null",
                "2/27/2000", false));

        MehoiavotAdapter adapter = new MehoiavotAdapter(getActivity(), mehoiavotArrayList);
        allTimeGv.setAdapter(adapter);

        allTimeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {
                Mehoiavot m = mehoiavotArrayList.get(i);

            }
        });


        return view;
    }

}






