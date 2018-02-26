package com.example.kash.cstimemanagement;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProjectTaskList extends android.support.v4.app.Fragment implements PTaskAdapter.clickListener{

    DBHelper db;
    private Cursor data;

    private List<PTask> pTaskList;

    private RecyclerView recyclerView;
    private PTaskAdapter mAdapter;
    private int projectId;
    private com.github.clans.fab.FloatingActionButton addPTask;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String projectTitle = bundle.getString("projectTitle");
        projectId = bundle.getInt("projectId");

        db = new DBHelper(getActivity());
        data = db.getProjectTaskData(projectId);
        pTaskList = new ArrayList<>();
        recyclerViewItems();
        super.onCreate(savedInstanceState);
       /* super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_task_list);

        Intent intentExtras = getIntent();
        final String projectName = intentExtras.getStringExtra("projectTitle");
        final int projectId = intentExtras.getExtras().getInt("projectId");

        //setTitle(projectName);
        */
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_project_task_list, null);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.project_task_list_recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new PTaskAdapter(pTaskList,getActivity(),data);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);

        addPTask = (com.github.clans.fab.FloatingActionButton)rootView.findViewById(R.id.project_task_list_add_task_button);
        addPTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),AddToProject.class);
                i.putExtra("projectId", projectId);
                startActivity(i);
            }
        });
        return rootView;
    }

    @Override
    public void onListItemClick(int itemIndex) {

    }
    public void recyclerViewItems(){
        int i = 0;

        if(data.getCount() >  0){//only starts checking cursor for data if theres any data in the database
            //first attempt to solve cursor initialisation issue
            // data.moveToFirst();

            while(data.moveToNext()){
                PTask ptask = new PTask(data.getString(3),data.getString(4),data.getInt(0),data.getInt(1),data.getInt(4),data.getInt(5),data.getInt(6),data.getLong(7),data.getLong(8));
                pTaskList.add(i,ptask);
                i++;
            }

        }else{
            Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
        }

    }
}
