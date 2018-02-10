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
    public static final String COL5 ="URGENCY";
    public static final String COL6 ="IMPORTANCE";
    public static final String COL7 ="PRIORITY";
    public static final String COL8 ="DATECREATED";

    public DBHelper(Context context) {
        super(context,DBNAME, null,1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLENAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TASKNAME TEXT, TASKDETAILS TEXT, ISCOMPLETE INTEGER, URGENCY INTEGER, IMPORTANCE INTEGER, PRIORITY INTEGER, DATE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(sqLiteDatabase);
    }
    public boolean addData(String tName, String tTitle,int isComplete, int urgency, int importance) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        int priority = getPriority(urgency,importance);
        contentValues.put(COL2, tName);
        contentValues.put(COL3, tTitle);
        contentValues.put(COL4,isComplete);
        contentValues.put(COL5, urgency);
        contentValues.put(COL6, importance);
        contentValues.put(COL7,priority);

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

    //calculate priority
    //top priority == 1 lowest = 4
    public int getPriority(int urgency, int importance){
        int priority;

        if((urgency == 1) & (importance == 1)){
            priority = 1;
        }else if((urgency == 0) & (importance == 1)){
            priority = 2;
        }else if((urgency == 1) & (importance == 0)){
            priority = 3;
        }else if((urgency == 0) & (importance == 0)){
            priority = 4;
        }else{
            priority = 4;
        }
        return priority;
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

    //return only incomplete tasks
    public Cursor getInCompleteTaskData(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLENAME + " WHERE " + COL4 + " = 0",null);
        return data;
    }

    //return tasks in order of priority (highest first)
    public Cursor getPrioritisedTaskData(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLENAME + " ORDER BY " + COL7 + " ASC",null);
        return data;
    }

    public void updateData(String tName, String tTitle,int id, int tUrgency, int tImportance) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, tName);
        contentValues.put(COL3, tTitle);
        contentValues.put(COL5,tUrgency);
        contentValues.put(COL6,tImportance);
        contentValues.put(COL7,getPriority(tUrgency,tImportance));
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
