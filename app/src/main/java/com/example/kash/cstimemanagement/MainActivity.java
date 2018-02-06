package com.example.kash.cstimemanagement;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.clickListener{

    DBHelper db;

    private RecyclerView recyclerView;
   // private RecyclerView.Adapter adapter;
    private Cursor mCursor;
    private Adapter mAdapter;

    private List<Task> taskList;

    private Adapter.clickListener mOnClickListener;
    FloatingActionButton add_task_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //switch activity to add task activity upon button click
        add_task_button = (FloatingActionButton) findViewById(R.id.add_task_button);
        add_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddActivity.class));
            }
        });

        db = new DBHelper(this);

        //create recycler view
        recyclerView = (RecyclerView)findViewById(R.id.RV_1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //retrieve data from database and store in Cursor object
        Cursor data = db.getData();

       //create list to hold task objects
       taskList = new ArrayList<>();
       int i = 0;
       if(data.getCount() !=  0){
            while(data.moveToNext()){
                Task task = new Task(data.getString(1),data.getString(2),data.getInt(0));
                taskList.add(i,task);
                i++;
            }

           //create recycler view adapter
           /*mAdapter = new Adapter(taskList,this, new Adapter.clickListener(){
               @Override
               public void onListItemClick(int itemIndex) {
                   Toast.makeText(MainActivity.this, "click test", Toast.LENGTH_SHORT).show();
               }
           });*/
           mAdapter = new Adapter(taskList,this);
           mAdapter.setClickListener(this);
           recyclerView.setAdapter(mAdapter);
           recyclerView.setLayoutManager((new LinearLayoutManager(this)));
       }else{
           Toast.makeText(MainActivity.this, "No data", Toast.LENGTH_SHORT).show();
       }
    }

    @Override
    public void onListItemClick(int itemIndex) {
        //Toast.makeText(MainActivity.this, "click test " + itemIndex, Toast.LENGTH_SHORT).show();
        Task t = taskList.get(itemIndex);

       // Toast.makeText(MainActivity.this, t.getTask() + " " + t.getTaskDetails() + " " + t.getTaskDBId() , Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this,ViewTask.class);
        i.putExtra("taskName",t.getTask());
        i.putExtra("taskDetails",t.getTaskDetails());
        i.putExtra("taskId", t.getTaskDBId());
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
