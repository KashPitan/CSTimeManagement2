package com.example.kash.cstimemanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ViewTask extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        setTitle("Edit Task");

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

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewTask.this);
                builder.setMessage("Save changes?");
                builder.setTitle("UPDATE TASK");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(taskTitle.length() != 0 && taskDescription.length() != 0){
                            UpdateData(sTaskTitle,sTaskDescription,taskId,isUrgent,isImportant);
                            //switch back to main activity where new task should be shown
                            startActivity(new Intent(ViewTask.this,MainActivity.class));
                        }else{
                            Toast.makeText(ViewTask.this, "Please Fill In", Toast.LENGTH_SHORT).show();

                        }

                        startActivity(new Intent(ViewTask.this,MainActivity.class));
                        Toast.makeText(ViewTask.this, "Changes Saved", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(ViewTask.this,MainActivity.class));

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
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewTask.this);
                builder.setMessage("Delete task " + taskName + "?");
                builder.setTitle("DELETE TASK");

                //delete task from database if no is clicked and return to Main Activity
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        db.deleteData(taskId);
                        startActivity(new Intent(ViewTask.this,MainActivity.class));
                        Toast.makeText(ViewTask.this, "Task Deleted", Toast.LENGTH_SHORT).show();
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
    public void UpdateData(String taskTitle, String taskDescription,int id,int taskUrgency,int taskImportance){
         db.updateData(taskTitle,taskDescription,id,taskUrgency,taskImportance);
        /*
        //display toast messages to show user whether or not data entry has been successful
        if(insertData == true){
            Toast.makeText(ViewTask.this, "Task added!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(ViewTask.this, "Error: Task could not be added", Toast.LENGTH_SHORT).show();
        }*/

    }
}
