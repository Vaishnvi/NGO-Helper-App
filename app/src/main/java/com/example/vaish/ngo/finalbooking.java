package com.example.vaish.ngo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class finalbooking extends AppCompatActivity {
    DatabaseHelper mydb;
    Button Save;
    EditText Name,Email,Contact,address,bdate;
    CheckBox fur,foo,books,clothes,bday,cash;
    String[]selected=new String[] {"N","N","N","N","N","N"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalbooking);
        Bundle bundle = getIntent().getExtras();
        final String message = bundle.getString("message");
        Toast.makeText(finalbooking.this,"Selected ngo is: "+message, Toast.LENGTH_SHORT).show();
        mydb=new DatabaseHelper(this);
        Save=(Button)findViewById(R.id.save);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<6;i++)
                    Toast.makeText(finalbooking.this,"Selected at "+i +"is"+selected[i],Toast.LENGTH_SHORT).show();
                addData(message);
                sendEmail();
            }
        });
        Name=(EditText)findViewById(R.id.editname);
        Email=(EditText)findViewById(R.id.editemail);
        Contact=(EditText)findViewById(R.id.editcontact);
        address=(EditText)findViewById(R.id.editaddress);
        bdate=(EditText)findViewById(R.id.editbday);
        fur=(CheckBox)findViewById(R.id.furniture);
        foo=(CheckBox)findViewById(R.id.food);
        books=(CheckBox)findViewById(R.id.Books);
        clothes=(CheckBox)findViewById(R.id.clothes);
        bday=(CheckBox)findViewById(R.id.Birthday);
        cash=(CheckBox)findViewById(R.id.Cash);

    }
    public void addData(String message)
    {
        boolean Isinserted=mydb.insertData1(Name.getText().toString(),
                Email.getText().toString(),
                Contact.getText().toString(),
                address.getText().toString(),
                bdate.getText().toString(),
                message,
                selected[0],
                selected[1],
                selected[2],
                selected[3],
                selected[4],
                selected[5]);
        if(Isinserted)
            Toast.makeText(finalbooking.this,"Data is inserted",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(finalbooking.this,"Data is not inserted",Toast.LENGTH_SHORT).show();
    }
    public void checkone(View v)
    {
         if(fur.isChecked())
             selected[0]="Y";
         else
             selected[0]="N";
        if(foo.isChecked())
            selected[1]="Y";
        else
            selected[1]="N";
        if(books.isChecked())
            selected[2]="Y";
        else
            selected[2]="N";
        if(clothes.isChecked())
            selected[3]="Y";
        else
            selected[3]="N";
        if(bday.isChecked())
            selected[4]="Y";
        else
            selected[4]="N";
        if(cash.isChecked())
            selected[5]="Y";
        else
            selected[5]="N";
    }

    @SuppressLint("LongLogTag")
    protected void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"vkhindkar@gmail.com"};
        String[] CC = {"jkhindkar@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Check the database");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Booking by me plz check the database ");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(finalbooking.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
