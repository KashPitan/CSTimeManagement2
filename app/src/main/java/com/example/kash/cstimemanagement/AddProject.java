package com.example.kash.cstimemanagement;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class AddProject extends AppCompatActivity {

    private EditText projectTitle;
    //private
    DBHelper db;

    Button addDataButton;
    Button backButton;

    private TextView displayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String stringDateToFormat;
    int intDateToFormat;

    private int dueDay = 0;
    private int dueMonth = 0;
    private int dueYear = 0;
    private int dueHour = 0;
    private int dueMinute = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        setTitle("Add a new Project");

        displayDate = (TextView)findViewById(R.id.activity_add_date_input);
        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dateDialog = new DatePickerDialog(AddProject.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,month,day);
                dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dateDialog.show();
            }}
        );

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {

                //month value is 1 short of being correct
                //month++;

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

        db = new DBHelper(this);
        projectTitle = (EditText) findViewById(R.id.add_project_name_input);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        backButton = (Button) findViewById(R.id.activity_add_project_button_back);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddProject.this,Main2Activity.class);
                //int ProjectFragment = android.R.id(ProjectFragment.class);
                i.putExtra("loadFrag", 1);
                startActivity(i);

                //startActivity(new Intent(AddProject.this,Main2Activity.class));
            }
        });


        addDataButton = (Button)findViewById(R.id.activity_add_project_button_save);
        addDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal3 = new GregorianCalendar(dueYear,dueMonth,dueDay,dueHour,dueMinute,0);
                Log.e("CALENDAR INPUT HOUR", "" + dueHour);
                cal3.set(Calendar.HOUR_OF_DAY, dueHour);
                long dueDateLong = cal3.getTimeInMillis();

                String pTitle = projectTitle.getText().toString();

                if(pTitle.length() != 0){
                    AddData(pTitle,dueDateLong);
                    Intent i = new Intent(AddProject.this,Main2Activity.class);
                    //int ProjectFragment = android.R.id(ProjectFragment.class);
                    i.putExtra("loadFrag", 1);
                    startActivity(i);
                    //startActivity(new Intent(AddProject.this,Main2Activity.class));

                }else{
                    projectTitle.setError("Please fill in");
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
            Intent i = new Intent(this,Main2Activity.class);
            //int ProjectFragment = android.R.id(ProjectFragment.class);
            i.putExtra("loadFrag", 1);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this,Main2Activity.class);
        //int ProjectFragment = android.R.id(ProjectFragment.class);
        i.putExtra("loadFrag", 1);
        startActivity(i);
    }

    public void AddData(String projectTitle, long dueDate){
        //task labelled incomplete upon creation
        boolean insertData = db.addProject(projectTitle,dueDate);

        //display toast messages to show user whether or not data entry has been successful
        if(insertData == true){
            Toast.makeText(AddProject.this, "Project added!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(AddProject.this, "Error: Project could not be added", Toast.LENGTH_SHORT).show();
        }

    }
}
