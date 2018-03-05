package com.example.kash.cstimemanagement;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProjectTaskList2 extends AppCompatActivity implements Adapter.clickListener{

    DBHelper db;
    private Cursor data;

    private List<Task> TaskList;

    private RecyclerView recyclerView;
    private Adapter mAdapter;
    private int projectId;
    private String ProjectTitle;
    private com.github.clans.fab.FloatingActionButton addPTask;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_task_list);

        //data from project fragment

        /*Bundle bundle = getArguments();
        String projectTitle = bundle.getString("projectTitle");
        projectId = bundle.getInt("projectId");
        ProjectTitle = projectTitle;
        */
        Intent intentExtras = getIntent();
        final String projectTitle = intentExtras.getStringExtra("projectTitle");
        final int projectId = intentExtras.getExtras().getInt("projectId");
        setTitle(projectTitle);

        db = new DBHelper(this);
        data = db.getData(projectId);
        TaskList = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.project_task_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new Adapter(TaskList,this,data);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addPTask = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.project_task_list_add_task_button);
        addPTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProjectTaskList2.this,AddToProject.class);
                i.putExtra("projectId", projectId);
                startActivity(i);
            }
        });
        recyclerViewItems();


       /* super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_task_list);

        Intent intentExtras = getIntent();
        final String projectName = intentExtras.getStringExtra("projectTitle");
        final int projectId = intentExtras.getExtras().getInt("projectId");

        //setTitle(projectName);
        */
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
    public void onListItemClick(int itemIndex) {
        Task t = TaskList.get(itemIndex);
        Intent i = new Intent(this,ViewPTask.class);
        i.putExtra("taskName",t.getTask());
        i.putExtra("taskDetails",t.getTaskDetails());
        i.putExtra("taskId", t.getTaskDBId());
        i.putExtra("taskUrgency",t.isUrgent());
        i.putExtra("taskImportance",t.isImportant());
        i.putExtra("taskPriority", t.getPriority());
        i.putExtra("taskDateCreated", t.getDateCreated());
        i.putExtra("taskDateDue", t.getDateDue());
        startActivity(i);

    }
    public void recyclerViewItems(){
        int i = 0;

        if(data.getCount() >  0){//only starts checking cursor for data if theres any data in the database
            //first attempt to solve cursor initialisation issue
            // data.moveToFirst();

            while(data.moveToNext()){
                Task task = new Task(data.getString(1),data.getString(2),data.getInt(0),data.getInt(4),data.getInt(5),data.getInt(6),data.getLong(7),data.getLong(8),data.getInt(9));
                TaskList.add(i,task);
                i++;
            }

        }else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }

    }
}
