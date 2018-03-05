package com.example.kash.cstimemanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLInput;

/**
 * Created by Kash on 24/01/2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 5;

    public static final String TABLENAME2 = "projects";
    public static final String PCOL1 = "PROJECTID";
    public static final String PCOL2 = "PROJECTDATECREATED";
    public static final String PCOL3 = "PROJECTDUEDATE";
    public static final String PCOL4 = "PROJECTTITLE";
    public static final String PCOL5 = "PROJECTISCOMPLETE";

    public static final String TABLENAME3 = "projectTasks";
    public static final String PTCOL0 = "PROJECTID";
    public static final String PTCOL1 = "PTASKID";
    public static final String PTCOL2 = "PTASKNAME";
    public static final String PTCOL3 = "PTASKDETAILS";
    public static final String PTCOL4 = "PISCOMPLETE";
    public static final String PTCOL5 = "PURGENCY";
    public static final String PTCOL6 = "PIMPORTANCE";
    public static final String PTCOL7 = "PPRIORITY";
    public static final String PTCOL9 = "PDATECREATED";
    public static final String PTCOL8 = "PDATEDUE";


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
    public static final String COL9 = "DATEDUE";
    public static final String COL10 = "PROJECTID";

    public DBHelper(Context context) {
        super(context,DBNAME, null,1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLENAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TASKNAME TEXT, TASKDETAILS TEXT, ISCOMPLETE INTEGER, URGENCY INTEGER, IMPORTANCE INTEGER, PRIORITY INTEGER, DATECREATED INTEGER, DATEDUE INTEGER, PROJECTID INTEGER)");
        sqLiteDatabase.execSQL("create table " + TABLENAME2 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, PROJECTTITLE TEXT, PROJECTDATECREATED INTEGER, PROJECTDUEDATE INTEGER, PROJECTISCOMPLETE INTEGER)");

        sqLiteDatabase.execSQL("create table " + TABLENAME3 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, PROJECTID INTEGER, PTASKNAME TEXT, PTASKDETAILS TEXT, PISCOMPLETE INTEGER, PURGENCY INTEGER, PIMPORTANCE INTEGER, PPRIORITY INTEGER, PDATECREATED INTEGER, PDATEDUE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLENAME2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLENAME3);
        onCreate(sqLiteDatabase);
    }
    public boolean addData(String tName, String tDetails,int isComplete, int urgency, int importance, long dateDue, int projectId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        int priority = getPriority(urgency,importance);
        Log.e("PRIORITY", "" + priority);

        contentValues.put(COL2, tName);
        contentValues.put(COL3, tDetails);
        contentValues.put(COL4,isComplete);
        contentValues.put(COL5, urgency);
        contentValues.put(COL6, importance);
        contentValues.put(COL7,priority);
        contentValues.put(COL8, System.currentTimeMillis());
        contentValues.put(COL9, dateDue);
        contentValues.put(COL10, projectId);

        long result = sqLiteDatabase.insert(TABLENAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addProject(String pName, long dateDue){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PCOL2, System.currentTimeMillis());
        contentValues.put(PCOL3, dateDue);
        contentValues.put(PCOL4, pName);
        contentValues.put(PCOL5,0);


        long result = sqLiteDatabase.insert(TABLENAME2, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void setProjectToComplete(int projectId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PCOL5, 1);
        String idString[] = {String.valueOf(projectId)};

        sqLiteDatabase.update(TABLENAME2, contentValues,"id=?", idString);

    }

    public boolean addPTask(String name, String details,int isComplete, int urgency, int importance, long dateDue,int projectId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        int priority = getPriority(urgency,importance);
        /*
        Log.d("URGENCY","" + urgency);
        Log.d("IMPORTANCE", "" + importance);
        Log.d("PRIORITY","" + priority);
        */
        contentValues.put(PTCOL0, projectId);
        contentValues.put(PTCOL2, name);
        contentValues.put(PTCOL3, details);//details?
        contentValues.put(PTCOL4,isComplete);
        contentValues.put(PTCOL5, urgency);
        contentValues.put(PTCOL6, importance);
        contentValues.put(PTCOL7,getPriority(urgency,importance));
        contentValues.put(PTCOL8, System.currentTimeMillis());
        contentValues.put(PTCOL9, dateDue);

        long result = sqLiteDatabase.insert(TABLENAME3, null, contentValues);

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

    public Cursor getData(int projectId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLENAME + " WHERE " + COL10 + " = " + projectId + " ORDER BY " + COL7 + " ASC," + COL9 + " ASC", null);
        return data;
    }

    //return only completed tasks
    public Cursor getCompleteData(int projectId){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLENAME + " WHERE " + COL4 + " = 1 " + "AND " + COL10 + " = " + projectId ,null);
        return data;
    }

    //return only incomplete tasks
    public Cursor getInCompleteTaskData(int projectId){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLENAME + " WHERE " + COL4 + " = 0 " + "AND " + COL10 + " = " + projectId + " ORDER BY " +  COL7 + " ASC," + COL9 + " ASC",null);
        return data;
    }

    //return tasks in order of priority (highest first)
    public Cursor getPrioritisedTaskData(int projectId){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLENAME + " WHERE " + COL4 + "= 0 " + "AND " + COL10 + " = " + projectId + " ORDER BY " + COL7 + " ASC," + COL9 + " ASC",null);
        return data;
    }

    public void updateData(String tName, String tTitle,int id, int tUrgency, int tImportance,long tDueDate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, tName);
        contentValues.put(COL3, tTitle);
        contentValues.put(COL5,tUrgency);
        contentValues.put(COL6,tImportance);
        contentValues.put(COL7,getPriority(tUrgency,tImportance));
        contentValues.put(COL9,tDueDate);

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

    public Cursor getProjectData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLENAME2 + " WHERE " + PCOL5 + " =0" +  " ORDER BY " + PCOL3 + " DESC", null);
        return data;
    }
    public Cursor getCompletedProjectData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLENAME2 + " WHERE " + PCOL5 + " =1", null);
        return data;
    }

    public Cursor getProjectTaskData(int projectId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLENAME3 + " WHERE " + PTCOL0 + " = " + projectId, null);
        return data;
    }

    public void deleteProjectTaskData(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String idString[] = {String.valueOf(id)};
        sqLiteDatabase.delete(TABLENAME3, "id=?", idString);
    }

    public void deleteProjectData(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String idString[] = {String.valueOf(id)};
        sqLiteDatabase.delete(TABLENAME3, "PROJECTID=?", idString);
        sqLiteDatabase.delete(TABLENAME2, "id=?", idString);

    }

    public void setPTaskToComplete(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PTCOL4, 1);
        String idString[] = {String.valueOf(id)};

        sqLiteDatabase.update(TABLENAME3, contentValues,"id=?", idString);

    }

    public Cursor getPriorityLevelTask(int priorityLevel){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        //String idString[] = {String.valueOf(id)};
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLENAME + " WHERE " + COL7 + " = " + priorityLevel + " AND " + COL4 + " =0" + " AND " + COL10 + " >0" , null);

        //sqLiteDatabase.update(TABLENAME3, contentValues,"id=?", idString);

        return data;
    }

    public void updatePTaskData(String ptName, String ptDetails,int id, int ptUrgency, int ptImportance,long ptDueDate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PTCOL2, ptName);
        contentValues.put(PTCOL3, ptDetails);
        contentValues.put(PTCOL5,ptUrgency);
        contentValues.put(PTCOL6,ptImportance);
        contentValues.put(PTCOL7,getPriority(ptUrgency,ptImportance));
        contentValues.put(PTCOL8,ptDueDate);

        String idString[] = {String.valueOf(id)};
        sqLiteDatabase.update(TABLENAME3,contentValues,"id=?",idString);

       /*sqLiteDatabase.execSQL("UPDATE " + TABLENAME + " SET " + COL2 + " = " + tName  + " , " +
                COL3 + " = " + tTitle + " WHERE " + COL1 + " = " + id);*/


        //change to boolean and return true if successful
    }

}
