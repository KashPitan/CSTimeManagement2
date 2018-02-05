package com.example.kash.cstimemanagement;

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

        db = new DBHelper(this);


    }
}
