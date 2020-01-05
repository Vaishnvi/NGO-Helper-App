package com.example.vaish.ngo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class Booking extends AppCompatActivity {
    Spinner spinner;
    String places;
    Button findNGOs;
//    String[] listItems;
 //   boolean[] checkItems;
   // ArrayList<Integer> mUserItems = new ArrayList<>();
    ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        findNGOs = (Button)findViewById(R.id.findNearbyNGOsButton);
        spinner = (Spinner) findViewById(R.id.spinnerEdit);
        adapter = ArrayAdapter.createFromResource(this,R.array.Places,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                places = spinner.getSelectedItem().toString();
                findNGOs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

     //   listItems = getResources().getStringArray(R.array.DonateItems);
     //   checkItems = new boolean[listItems.length];




    }
}
