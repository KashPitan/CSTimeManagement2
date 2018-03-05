package com.example.kash.cstimemanagement;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddActivity extends AppCompatActivity {

    DBHelper db;

    Button addDataButton;
    Button backButton;

    EditText title,description;

    CheckBox urgencyBox,importanceBox;
    private int isUrgent, isImportant;

    private TextView displayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String stringDateToFormat;
    int intDateToFormat;

    private TextView displayTime;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    String stringTimeToFormat;
    int intTimeToFormat;

    private int dueDay = 0;
    private int dueMonth = 0;
    private int dueYear = 0;
    private int dueHour = 0;
    private int dueMinute = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("New Task");

        displayDate = (TextView)findViewById(R.id.activity_add_date_input);
        displayDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dateDialog = new DatePickerDialog(AddActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,month,day);
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

                displayDate.setText(dateString);
            }
        };

        displayTime = (TextView)findViewById(R.id.activity_add_time_input);
        displayTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal2 = Calendar.getInstance();
                int hour = cal2.get(Calendar.HOUR_OF_DAY);
                int minute = cal2.get(Calendar.MINUTE);

                TimePickerDialog timeDialog = new TimePickerDialog(AddActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mTimeSetListener,hour,minute,true);
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
                displayTime.setText(timeString);
            }
        };

        importanceBox = (CheckBox)findViewById(R.id.activity_add_checkBox_importance);
        urgencyBox = (CheckBox)findViewById(R.id.activity_add_checkBox_urgency);

        //back button
        backButton = (Button) findViewById(R.id.activityAdd_button_back_);
        backButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddActivity.this,Main2Activity.class));
            }
        });

        //create database
        db = new DBHelper(this);

        title = (EditText) findViewById(R.id.task_name_input);
        description = (EditText) findViewById(R.id.task_details_input);
        addDataButton = (Button) findViewById(R.id.button_save);

        description.setText("");

        //back button in action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addDataButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal3 = new GregorianCalendar(dueYear,dueMonth,dueDay,dueHour,dueMinute,0);
                Log.e("CALENDAR INPUT HOUR", "" + dueHour);
                cal3.set(Calendar.HOUR_OF_DAY, dueHour);
                long dueDateLong = cal3.getTimeInMillis();

                String taskTitle = title.getText().toString();
                String taskDescription = description.getText().toString();
                if(importanceBox.isChecked()){
                    isImportant = 1;
                }else{
                    isImportant = 0;
                }
                if(urgencyBox.isChecked()){
                    isUrgent = 1;
                }else{
                    isUrgent = 0;
                }

                if(taskTitle.length() != 0 /*&& taskDescription.length() != 0*/){
                    AddData(taskTitle,taskDescription,isUrgent,isImportant,dueDateLong);
                    description.setText("");
                    title.setText("");
                    //Log.e("CALENDAR INPUT", " " + dueYear + " " + dueMonth + " " + dueDay + " " + dueHour + " " + dueMinute);
                    //Log.e("CALENDAR INPUT LONG", " " + dueDateLong);
                    //Toast.makeText(AddActivity.this, " " + dueDateLong, Toast.LENGTH_SHORT).show();

                    //switch back to main activity where new task should be shown
                    startActivity(new Intent(AddActivity.this,Main2Activity.class));
                }else{
                    title.setError("Please fill in");
                   // Toast.makeText(AddActivity.this, "Please Fill In", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void AddData(String taskTitle, String taskDescription, int isUrgent, int isImportant,long dueDate){
        //task labelled incomplete upon creation
        //input of projectid is -1 for "free floating tasks" (tasks not attached to a project)
       boolean insertData = db.addData(taskTitle,taskDescription,0,isUrgent, isImportant,dueDate,-1);

                //display toast messages to show user whether or not data entry has been successful
                if(insertData == true){
                    Toast.makeText(AddActivity.this, "Task added!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddActivity.this, "Error: Task could not be added", Toast.LENGTH_SHORT).show();
                }

    }
}
