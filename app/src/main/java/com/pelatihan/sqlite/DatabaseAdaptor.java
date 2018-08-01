package com.pelatihan.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by indon on 8/1/2018.
 */

public class DatabaseAdaptor{

    private DatabaseHelper dbHelper;
    private Context context;

    public DatabaseAdaptor(Context context) {
        dbHelper = new DatabaseHelper(context);
        this.context = context;
    }

//    Inserting Data to Database
    public long insert2DB(HashMap<String, String> data) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for(Map.Entry<String, String> values : data.entrySet()) {
            contentValues.put(values.getKey(), values.getValue());
        }
        long check = db.insert("users", null, contentValues);
        return check;
    }

//    Getting Data from Database
    public ArrayList<HashMap<String, String>> getData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = new String[]{"id", "username", "password"};
        Cursor cursor = db.query("users", columns, null, null, null, null, null);
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        while(cursor.moveToNext()) {
            HashMap<String, String> hm = new HashMap<>();
            for(int i=0; i<columns.length; i++) {
                String tmp = cursor.getString(cursor.getColumnIndex(columns[i]));
                hm.put(columns[i], tmp);
            }
            data.add(hm);
        }
        return data;
    }

//    Updating Data in Database
    public int updateData(String id, HashMap<String, String> data) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] whereArgs = new String[]{id};
        ContentValues contentValues = new ContentValues();
        for(Map.Entry<String, String> values : data.entrySet()) {
            contentValues.put(values.getKey(), values.getValue());
        }
        int check = db.update("users", contentValues, "id" + " = ?", whereArgs);
        return check;
    }

//    Deleting Data in Database
    public int deleteData(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] whereArgs = new String[]{id};
        int check = db.delete("users", "id" + " = ?", whereArgs);
        return check;
    }
}
