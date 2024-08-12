package com.example.recyclerwithdatabaseexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataLayer extends SQLiteOpenHelper {
    public DataLayer(@Nullable Context context) {
        super(context, "StudentDb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table Student(Id Integer Primary Key AUTOINCREMENT,Name Text not null,Address Text Not NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop Table If Exists Student");
        onCreate(db);
    }
    public boolean InsertData(Student s){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("Name",s.Name);
        values.put("Address",s.Address);
        long count=db.insert("Student",null,values);
        db.close();
        return count>0;
    }
    public ArrayList<Student> GetData(){
        ArrayList<Student>list=new ArrayList<Student>();
        try{
            SQLiteDatabase db=getReadableDatabase();
            Cursor cursor=db.rawQuery("Select Id,Name,Address From Student",null);
            while(cursor.moveToNext()){
                Student s=new Student();
                s.Id=cursor.getInt(0);
                s.Name=cursor.getString(1);
                s.Address=cursor.getString(2);
                list.add(s);
            }
            cursor.close();
            db.close();
        }
        catch(Exception ex){
            Log.e("DatabaseError.","Error while reading data from database.");
        }
        return list;
    }
    public boolean DeleteData(int Id){
        SQLiteDatabase db=getWritableDatabase();
        int count=db.delete("Student","Id?",new String[]{String.valueOf(Id)});
        db.close();
        return count==1;
    }
}
