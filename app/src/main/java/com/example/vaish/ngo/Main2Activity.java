package com.example.vaish.ngo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    DatabaseHelper mydb;
    Userdatabase uDb;
    Button bt,btviewall,btviewallUsers,btviewbooking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mydb=new DatabaseHelper(this);
        uDb=new Userdatabase(this);
        bt=(Button) findViewById(R.id.button);
        btviewall=(Button) findViewById(R.id.Viewngos);
        btviewallUsers=(Button) findViewById(R.id.viewusers);
        btviewbooking=(Button) findViewById(R.id.viewbooking);
        onclick();
        viewAll();
        viewUsers();
        viewBookings();
    }
    public void viewBookings()
    {
        btviewbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res=mydb.getData1();
                if(res.getCount()==0)
                {
                    //show message
                    showMessage("Error","Nothing Found");
                    return;
                }
                StringBuffer buffer2=new StringBuffer();
                while(res.moveToNext()){
                    buffer2.append("id_key : "+ res.getString(0)+"\n");
                    buffer2.append("Name : "+ res.getString(1)+"\n");
                    buffer2.append("email : "+ res.getString(2)+"\n");
                    buffer2.append("contact : "+ res.getString(3)+"\n");
                    buffer2.append("address : "+ res.getString(4)+"\n");
                    buffer2.append("Birthdate : "+ res.getString(5)+"\n");
                    buffer2.append("SelectedNgo : "+ res.getString(6)+"\n");
                    buffer2.append("Furniture : "+ res.getString(7)+"\n");
                    buffer2.append("Food : "+ res.getString(8)+"\n");
                    buffer2.append("Books : "+ res.getString(9)+"\n");
                    buffer2.append("Clothes : "+ res.getString(10)+"\n");
                    buffer2.append("BirthdayCelebration : "+ res.getString(11)+"\n");
                    buffer2.append("Cash : "+ res.getString(12)+"\n\n");
                }
                //show all data
                showMessage("Data",buffer2.toString());

            }
        });
    }
    public void viewUsers()
    {
        btviewallUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Cursor res1=uDb.getData();
               if(res1.getCount()==0)
               {
                   showMessage("Error","Nothing Found");
                   return;
               }
                StringBuffer buffer1=new StringBuffer();
                while(res1.moveToNext()){
                    buffer1.append("id_key : "+ res1.getString(0)+"\n");
                    buffer1.append("user_name : "+ res1.getString(1)+"\n");
                    buffer1.append("email_id : "+ res1.getString(2)+"\n");
                    buffer1.append("pass : "+ res1.getString(3)+"\n\n");
                }
                //show all data
                showMessage("Data",buffer1.toString());
            }
        });
    }
    public void onclick()
    {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Main2Activity.this,"hey done",Toast.LENGTH_SHORT).show();
                Intent j=new Intent(Main2Activity.this,Add.class);
                startActivity(j);

            }
        });
    }
    public void viewAll()
    {
        btviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res=mydb.getData();
                if(res.getCount()==0)
                {
                    //show message
                    showMessage("Error","Nothing Found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("id_key : "+ res.getString(0)+"\n");
                    buffer.append("name : "+ res.getString(1)+"\n");
                    buffer.append("pincode : "+ res.getString(2)+"\n");
                    buffer.append("lon : "+ res.getString(3)+"\n");
                    buffer.append("lat : "+ res.getString(4)+"\n\n");
                }
                //show all data
                showMessage("Data",buffer.toString());

            }
        });
    }
    public void showMessage(String title,String Message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
