package com.example.vaish.ngo;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addngos extends AppCompatActivity {
    DatabaseHelper mydb;
    EditText name,pin,lon,lat;
    Button Add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addngos);
        mydb=new DatabaseHelper(this);
        name=(EditText) findViewById(R.id.name);
        pin=(EditText) findViewById(R.id.pincode);
        lon=(EditText) findViewById(R.id.lon);
        lat=(EditText) findViewById(R.id.lat);
        Add=(Button) findViewById(R.id.button);
        AddData();

    }
    public void AddData()
    {
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean Isinserted=mydb.insertData(name.getText().toString(),
                        pin.getText().toString(),
                        lon.getText().toString(),
                        lat.getText().toString());
                if(Isinserted)
                    Toast.makeText(addngos.this,"Data is inserted",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(addngos.this,"Data is not inserted",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
