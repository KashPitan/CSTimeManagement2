package com.example.kash.cstimemanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLInput;

/**
 * Created by Kash on 24/01/2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "task_database";
    public static final String TABLENAME = "tasks";

    public static final String COL1 ="TASKID";
    public static final String COL2 ="TASKNAME";
    public static final String COL3 ="TASKDETAILS";

    public DBHelper(Context context) {
        super(context,DBNAME, null,1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLENAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TASKNAME TEXT, TASKDETAILS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(sqLiteDatabase);
    }
    public boolean addData(String tName, String tTitle) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, tName);
        contentValues.put(COL3, tTitle);

        long result = sqLiteDatabase.insert(TABLENAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLENAME, null);
        return data;
    }
}
