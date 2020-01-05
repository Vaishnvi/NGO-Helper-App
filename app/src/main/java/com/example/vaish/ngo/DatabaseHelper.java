package com.example.vaish.ngo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vaish on 16/03/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String Database_name = "ngo.db";
    public static final String table_name = "ngo_listing";
    public static final String col1 = "id_key";
    public static final String col2 = "name";
    public static final String col3 = "pincode";
    public static final String col4 = "lon";
    public static final String col5 = "lat";

    public static final String table_name1 = "UserBooking";
    public static final String cola1 = "id_key";
    public static final String cola2 = "Name";
    public static final String cola3 = "email";
    public static final String cola4 = "contact";
    public static final String cola5 = "address";
    public static final String cola6 = "Birthdate";
    public static final String cola7 = "SelectedNgo";
    public static final String cola8 = "Furniture";
    public static final String cola9 = "Food";
    public static final String cola10 = "Books";
    public static final String cola11 = "Clothes";
    public static final String cola12 = "BirthdayCelebration";
    public static final String cola13 = "Cash";




    public DatabaseHelper(Context context) {
        super(context, Database_name,null,2);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table_name +"( id_key INTEGER primary key autoincrement , name TEXT, pincode TEXT ,lon REAL, lat REAL)");
        db.execSQL("select * from " + table_name);

    }
    public void  onCreate1(SQLiteDatabase db)
    {
        db.execSQL("create table " + table_name1 +"( id_key INTEGER primary key autoincrement , Name TEXT, email TEXT , contact TEXT , address TEXT , Birthdate TEXT , SelectedNgo TEXT , Furniture TEXT , Food TEXT , Books TEXT , Clothes TEXT , BirthdayCelebration TEXT , Cash TEXT )");
        db.execSQL("select * from " + table_name1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ table_name);
        onCreate(db);
        db.execSQL("drop table if exists "+ table_name1);
        onCreate1(db);
    }
    public boolean insertData(String name,String pincode,String lon,String lat )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col2,name);
        contentValues.put(col3,pincode);
        contentValues.put(col4,lon);
        contentValues.put(col5,lat);
        long result=db.insert(table_name,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }
    public boolean insertData1(String Name,String email,String contact,String address,String Birthdate,String SelectedNgo,String Furniture,String Food,String Books,String Clothes,String BirthdayCelebration,String Cash)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(cola2,Name);
        contentValues.put(cola3,email);
        contentValues.put(cola4,contact);
        contentValues.put(cola5,address);
        contentValues.put(cola6,Birthdate);
        contentValues.put(cola7,SelectedNgo);
        contentValues.put(cola8,Furniture);
        contentValues.put(cola9,Food);
        contentValues.put(cola10,Books);
        contentValues.put(cola11,Clothes);
        contentValues.put(cola12,BirthdayCelebration);
        contentValues.put(cola13,Cash);
        long result=db.insert(table_name1,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }
    public Cursor getData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+table_name,null);
        return res;
    }
    public Cursor getData1()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+table_name1,null);
        return res;
    }
}
