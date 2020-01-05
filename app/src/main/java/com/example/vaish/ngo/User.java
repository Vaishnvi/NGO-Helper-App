package com.example.vaish.ngo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class User extends AppCompatActivity {

    Userdatabase ud;
    Button bt;
    EditText n1,e1,p1,cp1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ud=new Userdatabase(this);
        bt=(Button) findViewById(R.id.register);
        n1=(EditText) findViewById(R.id.regname);
        e1=(EditText) findViewById(R.id.regemail);
        p1=(EditText) findViewById(R.id.regpass);
        cp1=(EditText) findViewById(R.id.regconfirmpass);
        adddata();
    }
    public void adddata()
    {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(p1.getText().toString().equals(cp1.getText().toString()))
                {
                    boolean isinserted=ud.insertData(n1.getText().toString(),
                            e1.getText().toString(),
                            p1.getText().toString());
                    if(isinserted)
                        Toast.makeText(User.this,"Data is inserted",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(User.this,"Data is not inserted",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(User.this,"Passwords Dont match",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
