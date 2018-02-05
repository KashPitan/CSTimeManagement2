package com.example.kash.cstimemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    DBHelper db;
    Button addDataButton;
    Button backButton;
    EditText title,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //back button
        backButton = (Button) findViewById(R.id.activityAdd_button_back_);
        backButton.setOnClickListener(new View.OnClickListener(){
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

        addDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskTitle = title.getText().toString();
                String taskDescription = description.getText().toString();

                if(taskTitle.length() != 0 && taskDescription.length() != 0){
                    AddData(taskTitle,taskDescription);
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

    public void AddData(String taskTitle, String taskDescription){
       boolean insertData = db.addData(taskTitle,taskDescription);

                //display toast messages to show user whether or not data entry has been successful
                if(insertData == true){
                    Toast.makeText(AddActivity.this, "Task added!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddActivity.this, "Error: Task could not be added", Toast.LENGTH_SHORT).show();
                }



    }

}
