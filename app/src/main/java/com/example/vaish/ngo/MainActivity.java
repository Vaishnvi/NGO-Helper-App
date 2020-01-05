package com.example.vaish.ngo;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Userdatabase udb;
    EditText email,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        udb=new Userdatabase(this);
        Button bt=(Button) findViewById(R.id.login);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=(EditText) findViewById(R.id.Emailid);
                final String e1=email.getText().toString();
                pass=(EditText) findViewById(R.id.password);
                final String admin=new String("admin");
                final String p1=pass.getText().toString();
                final String pa=new String("admin");
                if(e1.equals(admin) && p1.equals(pa))
                {
                    Toast.makeText(MainActivity.this,"Hey done",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(i);
                }
                else
                {
                    verifyFromSQLite(e1,p1);
                    emptyInputEditText();
                }
            }
        });
        Button reg=(Button) findViewById(R.id.Register);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,User.class);
                startActivity(i);
            }
        });
    }


    private void emptyInputEditText() {
        email.setText(null);
        pass.setText(null);
    }
    public void verifyFromSQLite(String e1,String p1)
    {
        //boolean ans;
        if(udb.checkUser(e1,p1))
        {
            Intent i=new Intent(MainActivity.this,Bookings.class);
            startActivity(i);
        }
        else
        {
            Toast.makeText(MainActivity.this,"Hey not done",Toast.LENGTH_SHORT).show();
        }
    }
    /*public void onclick(final String e1, final String p1,Button bt)
    {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(e1.equals("admin") && p1.equals("admin"))
                {
                    Toast.makeText(MainActivity.this,"Hey done",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(i);
                }
                else
                    Toast.makeText(MainActivity.this,"Hey not done",Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}
