package com.example.kash.cstimemanagement;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kash on 18/02/2018.
 */

public class CompletedTasksFragment extends android.support.v4.app.Fragment implements Adapter.clickListener {

    DBHelper db;
    private Cursor data;

    private List<Task> taskList;

    private RecyclerView recyclerView;
    private Adapter mAdapter;

    private ArrayAdapter<String> mArrayAdapter;
    private com.github.clans.fab.FloatingActionButton add_task_button;
    private com.github.clans.fab.FloatingActionButton add_project_button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        db = new DBHelper(getActivity());
        data = db.getCompleteData();
        taskList = new ArrayList<>();
        recyclerViewItems();

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.prioritised_fragment_layout, null);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.prioritised_fragment_recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new Adapter(taskList,getActivity(),data);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);


        add_task_button = (com.github.clans.fab.FloatingActionButton)rootView.findViewById(R.id.fab_menu_1);
        add_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AddActivity.class));
            }
        });

        add_project_button = (com.github.clans.fab.FloatingActionButton)rootView.findViewById(R.id.fab_menu_2);
        add_project_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AddProject.class));
            }
        });

        return rootView;
    }

    public void recyclerViewItems(){
        int i = 0;

        if(data.getCount() >  0){//only starts checking cursor for data if theres any data in the database
            //first attempt to solve cursor initialisation issue
            //data.moveToFirst();

            while(data.moveToNext()){
                Task task = new Task(data.getString(1),data.getString(2),data.getInt(0),data.getInt(4),data.getInt(5),data.getInt(6),data.getLong(7),data.getLong(8));
                taskList.add(i,task);
                i++;
            }

        }else{
            Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {//similiar to oncreate method
        //note use view.findview by id for layout resources

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onListItemClick(int itemIndex) {
        //Toast.makeText(MainActivity.this, "click test " + itemIndex, Toast.LENGTH_SHORT).show();
        Task t = taskList.get(itemIndex);
        Log.d("NOTE: "," " + (itemIndex));

        //Toast.makeText(MainActivity.this, t.getTask() + " " + t.getTaskDetails() + " " + t.getTaskDBId() , Toast.LENGTH_SHORT).show();
        // Toast.makeText(MainActivity.this,t.isImportant() + " " + t.isUrgent(),Toast.LENGTH_SHORT).show();
        //Toast.makeText(MainActivity.this,t.getPriority() + "",Toast.LENGTH_SHORT).show();
        //Toast.makeText(MainActivity.this,t.getDateCreated() + "",Toast.LENGTH_SHORT).show();


        Intent i = new Intent(getActivity(),ViewTask.class);
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
}

