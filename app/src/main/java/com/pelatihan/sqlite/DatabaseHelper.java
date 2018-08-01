package com.pelatihan.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by indon on 8/1/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

//    Database Configuration
    private static final String DATABASE_NAME = "sqlite.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

//    Creating Table
    @Override
    public void onCreate(SQLiteDatabase db){
        try{
            db.execSQL(createTable());
        } catch(Exception e) {
            Toast.makeText(context,"onCreate\n" + e,Toast.LENGTH_SHORT).show();
        }
    }

//    Upgrade Table
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        try{
            db.execSQL(dropTable());
            onCreate(db);
        } catch(Exception e) {
            Toast.makeText(context,"onUpgrade\n" + e,Toast.LENGTH_SHORT).show();
        }
    }

//    Query For Make Table
    private String createTable() {
        String query = "CREATE TABLE IF NOT EXISTS `users`" +
                "(" +
                "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "`username` VARCHAR(12) NOT NULL," +
                "`password` TEXT NOT NULL" +
                ");";
        return query;
    }

//    Query For Drop Table
    private String dropTable() {
        String query = "DROP TABLE IF EXISTS users;";
        return query;
    }
}
