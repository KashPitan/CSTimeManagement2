package com.example.kash.cstimemanagement;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ViewPTask extends AppCompatActivity {
    DBHelper db;

    EditText taskTitle;
    EditText taskDescription;
    CheckBox urgentBox,importantBox;
    Button editButton;
    Button deleteButton;
    private int isUrgent, isImportant;
    TextView dateCreated;
    TextView timeDue;
    TextView dateDue;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String stringDateToFormat;
    int intDateToFormat;

    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    String stringTimeToFormat;
    int intTimeToFormat;

    private int dueDay = 0;
    private int dueMonth = 0;
    private int dueYear = 0;
    private int dueHour = 0;
    private int dueMinute = 0;

    private long newDueDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ptask);

        //back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //retrieve intent from main activity
        Intent intentExtras = getIntent();

        final String taskName = intentExtras.getStringExtra("taskName");
        final String taskDetails = intentExtras.getStringExtra("taskDetails");
        final int taskId =  intentExtras.getExtras().getInt("taskId");
        final boolean taskUrgency = intentExtras.getExtras().getBoolean("taskUrgency");
        final boolean taskImportance = intentExtras.getExtras().getBoolean("taskImportance");
        final long taskDateCreated = intentExtras.getExtras().getLong("taskDateCreated");
        final long taskDateDue = intentExtras.getExtras().getLong("taskDateDue");

        setTitle("Edit Task: " + taskName);

        taskTitle = (EditText) findViewById(R.id.view_task_title);
        taskDescription = (EditText) findViewById(R.id.view_task_description);
        urgentBox = (CheckBox) findViewById(R.id.activity_view_task_urgent_checkBox);
        importantBox = (CheckBox) findViewById(R.id.activity_view_task_important_checkBox);
        dateCreated = (TextView)findViewById(R.id.activity_view_task_date_created);
        dateDue = (TextView)findViewById(R.id.activity_view_task_date_due);
        timeDue = (TextView)findViewById(R.id.activity_view_task_time_due);

        long longDateDue = taskDateDue;
        String displayDueDate = formatDate(longDateDue, "dd/MM/yyyy");
        dateDue.setText(displayDueDate);
        String displayDueTime = formatDate(longDateDue, "HH:mm");
        timeDue.setText(displayDueTime);

        Calendar previousDate = Calendar.getInstance();
        previousDate.setTimeInMillis(longDateDue);

        dueDay = previousDate.get(Calendar.DAY_OF_MONTH);
        dueMonth = previousDate.get(Calendar.MONTH);
        dueYear = previousDate.get(Calendar.YEAR);
        dueHour = previousDate.get(Calendar.HOUR_OF_DAY);
        dueMinute = previousDate.get(Calendar.MINUTE);


        dateDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dateDialog = new DatePickerDialog(ViewPTask.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,month,day);
                dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dateDialog.show();
            }}
        );

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {

                //month value is 1 short of being correct
                month++;

                //save the input values to convert to long value for later
                dueYear = year;
                dueMonth = month;
                dueDay = day;

                String dateString = day + "/" + month + "/" + year;
                stringDateToFormat = String.valueOf(day) + String.valueOf(month) + String.valueOf(year);
                intDateToFormat = Integer.parseInt(stringDateToFormat);
                //Toast.makeText(AddActivity.this, " " + intDateToFormat, Toast.LENGTH_SHORT).show();
                SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");

                dateDue.setText(dateString);
            }
        };

        timeDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal2 = Calendar.getInstance();
                int hour = cal2.get(Calendar.HOUR_OF_DAY);
                int minute = cal2.get(Calendar.MINUTE);

                TimePickerDialog timeDialog = new TimePickerDialog(ViewPTask.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mTimeSetListener,hour,minute,true);
                timeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timeDialog.show();
            }
        });

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {

                dueHour = hour;
                dueMinute = minute;

                String timeString = hour + ":" + minute;
                stringTimeToFormat = String.valueOf(hour) + String.valueOf(minute);
                intTimeToFormat = Integer.parseInt(stringTimeToFormat);
                //Toast.makeText(AddActivity.this, " " + intDateToFormat, Toast.LENGTH_SHORT).show();
                SimpleDateFormat dateFormat = new SimpleDateFormat(" HHmm");

                //Date date  = dateFormat.parse(stringDateToFormat.toString());
                timeDue.setText(timeString);
            }
        };


        long longDateCreated = taskDateCreated;
        String displayDate = formatDate(longDateCreated,"dd/MM/yyyy");
        dateCreated.setText(displayDate);

        taskTitle.setText(taskName);
        taskDescription.setText(taskDetails);
        db = new DBHelper(this);

        //set initial state of urgency box
        if(taskUrgency){//if taskUrgency is true
            urgentBox.setChecked(true);
        }else {
            urgentBox.setChecked(false);
        }

        //set initial state of importance box
        if(taskImportance){//if taskImportance is true
            importantBox.setChecked(true);
        }else {
            importantBox.setChecked(false);
        }

        editButton = (Button)findViewById(R.id.view_task_save_changes_button);
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String sTaskTitle = taskTitle.getText().toString();
                final String sTaskDescription = taskDescription.getText().toString();
                Calendar calNew = new GregorianCalendar(dueYear,dueMonth -1,dueDay,dueHour,dueMinute,0);
                newDueDate = calNew.getTimeInMillis();

                if(urgentBox.isChecked()){
                    isUrgent = 1;

                }else{
                    isUrgent = 0;
                }

                if(importantBox.isChecked()){
                    isImportant = 1;
                }else{
                    isImportant = 0;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewPTask.this);
                builder.setMessage("Save changes?");
                builder.setTitle("UPDATE TASK");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(taskTitle.length() != 0 /*&& taskDescription.length() != 0*/){
                            UpdateData(sTaskTitle,sTaskDescription,taskId,isUrgent,isImportant,newDueDate);
                            //switch back to main activity where new task should be shown
                            finish();
                            //startActivity(new Intent(ViewPTask.this,Main2Activity.class));
                        }else{
                            taskTitle.setError("Please fill in");
                            //Toast.makeText(ViewTask.this, "Please Fill In", Toast.LENGTH_SHORT).show();

                        }
                        finish();
                        //startActivity(new Intent(ViewPTask.this,Main2Activity.class));
                        Toast.makeText(ViewPTask.this, "Changes Saved", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        //startActivity(new Intent(ViewPTask.this,Main2Activity.class));

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }

        });

        deleteButton = (Button)findViewById(R.id.view_task_delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewPTask.this);
                builder.setMessage("Delete task " + taskName + "?");
                builder.setTitle("DELETE TASK");

                //delete task from database if no is clicked and return to Main Activity
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        db.deleteData(taskId);
                        startActivity(new Intent(ViewPTask.this,Main2Activity.class));
                        Toast.makeText(ViewPTask.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            //Intent i = new Intent(this,Main2Activity.class);
            //startActivity(i);
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public String formatDate(long milli, String format){
        DateFormat formatter = new SimpleDateFormat(format);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milli);
        return formatter.format(calendar.getTime());
    }
    public void UpdateData(String taskTitle, String taskDescription,int id,int taskUrgency,int taskImportance,long taskDueDate){
         db.updatePTaskData(taskTitle,taskDescription,id,taskUrgency,taskImportance,taskDueDate);
        /*
        //display toast messages to show user whether or not data entry has been successful
        if(insertData == true){
            Toast.makeText(ViewTask.this, "Task added!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(ViewTask.this, "Error: Task could not be added", Toast.LENGTH_SHORT).show();
        }*/

    }
}
