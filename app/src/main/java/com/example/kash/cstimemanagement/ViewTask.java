package com.example.kash.cstimemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewTask extends AppCompatActivity {
    DBHelper db;
    TextView taskTitle;
    TextView taskDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        //retrieve intent from main activity
        Intent intentExtras = getIntent();

        String taskName = intentExtras.getStringExtra("taskName");
        String taskDetails = intentExtras.getStringExtra("taskDetails");

        taskTitle = (TextView)findViewById(R.id.view_task_title);
        taskDescription = (TextView)findViewById(R.id.view_task_description);

        taskTitle.setText(taskName);
        taskDescription.setText(taskDetails);
        db = new DBHelper(this);


    }
}
