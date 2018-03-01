package com.example.kash.cstimemanagement;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class AddProject2 extends android.support.v4.app.Fragment  {

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
    public void onCreate(Bundle savedInstanceState) {
        db = new DBHelper(getActivity());
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_add_project,null);

        ((Main2Activity)getActivity()).setActionBarTitle("Add a new project");
        //((Main2Activity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //((Main2Activity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);


        displayDate = (TextView)rootView.findViewById(R.id.activity_add_date_input);
        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dateDialog = new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,month,day);
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

        projectTitle = (EditText) rootView.findViewById(R.id.add_project_name_input);
        /*
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        */
        backButton = (Button) rootView.findViewById(R.id.activity_add_project_button_back);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(),Main2Activity.class));

               // ((Main2Activity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                //((Main2Activity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();

                ProjectFragment projectFragment = new ProjectFragment();

                ft.replace(R.id.content_main2_frame_layout, projectFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        addDataButton = (Button)rootView.findViewById(R.id.activity_add_project_button_save);
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
                   // ((Main2Activity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    //((Main2Activity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();

                    ProjectFragment projectFragment = new ProjectFragment();

                    ft.replace(R.id.content_main2_frame_layout, projectFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    //startActivity(new Intent(getActivity(),Main2Activity.class));

                }else{
                    projectTitle.setError("Please fill in");
                }
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void AddData(String projectTitle, long dueDate){
        //task labelled incomplete upon creation
        boolean insertData = db.addProject(projectTitle,dueDate);

        //display toast messages to show user whether or not data entry has been successful
        if(insertData == true){
            Toast.makeText(getActivity(), "Project added!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), "Error: Project could not be added", Toast.LENGTH_SHORT).show();
        }

    }
}
