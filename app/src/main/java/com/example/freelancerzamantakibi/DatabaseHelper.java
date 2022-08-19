package com.example.freelancerzamantakibi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String FREELANCER_TABLE = "FREELANCER_TABLE";
    public static final String EMAIL = "EMAIL";
    public static final String NAME = "NAME";
    public static final String SURNAME = "SURNAME";
    public static final String ROLE = "ROLE";
    public static final String PHONE = "PHONE";
    public static final String PASSWORD = "PASSWORD";
    public static final String ABOUT = "ABOUT";
    public DatabaseHelper(@Nullable Context context) {
        super(context, "freelancer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + FREELANCER_TABLE + " ("+ EMAIL + " TEXT PRIMARY KEY, " + NAME + " TEXT, " + SURNAME + " TEXT, " + ROLE + " TEXT, "+ PHONE + " TEXT, "+ PASSWORD + " TEXT, " + ABOUT +" TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public boolean addOne(Freelancer freelancer){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EMAIL,freelancer.getEmail());
        cv.put(NAME,freelancer.getName());
        cv.put(SURNAME,freelancer.getSurname());
        cv.put(ROLE,freelancer.getRole());
        cv.put(PHONE,freelancer.getPhone());
        cv.put(PASSWORD,freelancer.getPassword());
        cv.put(ABOUT,freelancer.getPersonalInfo());

        long insert = db.insert(FREELANCER_TABLE, null, cv);
        if(insert==-1){
            return false;
        }
        else{
            return true;
        }

    }
    public boolean deleteOne(Freelancer freelancer){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + FREELANCER_TABLE + " WHERE " + EMAIL + " = " + freelancer.getEmail();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }


    }
    public List<Freelancer> getEveryOne(){
        List<Freelancer> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + FREELANCER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            do {
                String email = cursor.getString(0);
                String name = cursor.getString(1);
                String surname = cursor.getString(2);
                String role =cursor.getString(3);
                String phone =cursor.getString(4);
                String password = cursor.getString(5);
                String about= cursor.getString(6);
                Freelancer freelancer = new Freelancer(name,surname,role,password,phone,email,about);
                returnList.add(freelancer);

            } while(cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;
    }
    public boolean checkUser(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectString = "SELECT * FROM " + FREELANCER_TABLE + " WHERE " + EMAIL + " = " + email + " AND " + PASSWORD + " = " + password;
        Cursor cursor = db.rawQuery(selectString,null);
        boolean exist = false;
        if(cursor.getCount()>0){
            exist=true;


        }
        db.close();
        cursor.close();
        return exist;
    }
}
