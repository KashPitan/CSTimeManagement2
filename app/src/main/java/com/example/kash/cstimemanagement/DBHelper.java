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
    public static final String COL4 ="ISCOMPLETE";

    public DBHelper(Context context) {
        super(context,DBNAME, null,1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLENAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TASKNAME TEXT, TASKDETAILS TEXT, ISCOMPLETE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(sqLiteDatabase);
    }
    public boolean addData(String tName, String tTitle,int isComplete) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, tName);
        contentValues.put(COL3, tTitle);
        contentValues.put(COL4,isComplete);

        long result = sqLiteDatabase.insert(TABLENAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor getItemId(int itemIndex){
        return null;
    }

    public Cursor getData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLENAME, null);
        return data;
    }

    //return only completed tasks
    public Cursor getCompleteData(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLENAME + " WHERE " + COL4 + " = 1",null);
        return data;
    }

    public Cursor getInCompleteTaskData(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLENAME + " WHERE " + COL4 + " = 0",null);
        return data;
    }

    public void updateData(String tName, String tTitle,int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, tName);
        contentValues.put(COL3, tTitle);
        String idString[] = {String.valueOf(id)};
        sqLiteDatabase.update(TABLENAME,contentValues,"id=?",idString);

       /*sqLiteDatabase.execSQL("UPDATE " + TABLENAME + " SET " + COL2 + " = " + tName  + " , " +
                COL3 + " = " + tTitle + " WHERE " + COL1 + " = " + id);*/


        //change to boolean and return true if successful
    }
    public void deleteData(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String idString[] = {String.valueOf(id)};
        sqLiteDatabase.delete(TABLENAME, "id=?", idString);

    }

    public void setToComplete(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL4, 1);
        String idString[] = {String.valueOf(id)};

        sqLiteDatabase.update(TABLENAME, contentValues,"id=?", idString);

    }
}
