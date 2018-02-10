package com.example.kash.cstimemanagement;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        /*displayDate = (TextView)findViewById(R.id.activity_add_date_input);
        displayDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dateDialog = new DatePickerDialog(AddActivity.this,android.R.style.Theme_Holo_Dialog_MinWidth,mDateSetListener,year,month,day);
                dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dateDialog.show();
            }}

        );

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String dateString = day + "/" + month + "/" + year;
                stringDateToFormat = String.valueOf(day) + String.valueOf(month) + String.valueOf(year);
                intDateToFormat = Integer.parseInt(stringDateToFormat);
                //Toast.makeText(AddActivity.this, " " + intDateToFormat, Toast.LENGTH_SHORT).show();
                SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");

                //Date date  = dateFormat.parse(stringDateToFormat.toString());
                displayDate.setText(dateString);
            }
        };*/

        importanceBox = (CheckBox)findViewById(R.id.activity_add_checkBox_importance);
        urgencyBox = (CheckBox)findViewById(R.id.activity_add_checkBox_urgency);

        //back button
        backButton = (Button) findViewById(R.id.activityAdd_button_back_);
        backButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddActivity.this,MainActivity.class));
            }
        });

        //create database
        db = new DBHelper(this);
        title = (EditText) findViewById(R.id.task_name_input);
        description = (EditText) findViewById(R.id.task_details_input);
        addDataButton = (Button) findViewById(R.id.button_save);

        addDataButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
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

                if(taskTitle.length() != 0 && taskDescription.length() != 0){
                    AddData(taskTitle,taskDescription,isUrgent,isImportant);
                    description.setText("");
                    title.setText("");
                    //switch back to main activity where new task should be shown
                    startActivity(new Intent(AddActivity.this,MainActivity.class));
                }else{
                    Toast.makeText(AddActivity.this, "Please Fill In", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void AddData(String taskTitle, String taskDescription,int isUrgent, int isImportant){
        //task labelled incomplete upon creation
       boolean insertData = db.addData(taskTitle,taskDescription,0,isUrgent, isImportant);

                //display toast messages to show user whether or not data entry has been successful
                if(insertData == true){
                    Toast.makeText(AddActivity.this, "Task added!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddActivity.this, "Error: Task could not be added", Toast.LENGTH_SHORT).show();
                }

    }
}
