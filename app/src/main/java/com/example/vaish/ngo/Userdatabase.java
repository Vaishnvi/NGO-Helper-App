package com.example.vaish.ngo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vaish on 16/03/18.
 */

public class Userdatabase extends SQLiteOpenHelper{

    public static final String Databasename = "users.db";
    public static final String tablename = "users_listing";
    public static final String col1 = "id_key";
    public static final String col2 = "user_name";
    public static final String col3 = "email_id";
    public static final String col4 = "pass";


    public Userdatabase(Context context) {
        super(context, Databasename,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + tablename +"( id_key INTEGER primary key autoincrement , user_name TEXT, email_id TEXT, pass TEXT)");
        db.execSQL("select * from " + tablename);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ tablename);
        onCreate(db);
    }
    public boolean insertData(String user_name,String email_id,String pass )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col2,user_name);
        contentValues.put(col3,email_id);
        contentValues.put(col4,pass);
        long result=db.insert(tablename,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }
    public Cursor getData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+tablename,null);
        return res;
    }
    public boolean checkUser(String email,String password)
    {
       String[]columns={col1};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection=col3 + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor=db.query(tablename,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}
