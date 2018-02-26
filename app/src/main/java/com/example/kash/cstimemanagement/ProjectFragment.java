package com.example.kash.cstimemanagement;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
//import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kash on 25/02/2018.
 */

public class ProjectFragment extends android.support.v4.app.Fragment implements ProjectAdapter.clickListener {

    DBHelper db;
    private Cursor data;

    private List<Project> ProjectList;

    private RecyclerView recyclerView;
    private ProjectAdapter mAdapter;

    private com.github.clans.fab.FloatingActionButton addProject;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        db = new DBHelper(getActivity());
        data = db.getProjectData();
        ProjectList = new ArrayList<>();
        recyclerViewItems();
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.project_fragment_layout, null);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.project_fragment_recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ProjectAdapter(ProjectList,getActivity(),data);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);

        addProject = (com.github.clans.fab.FloatingActionButton)rootView.findViewById(R.id.project_fragment_add_project_button);
        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AddProject.class));
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onListItemClick(int itemIndex) {
        Project p = ProjectList.get(itemIndex);

       // android.support.v4.app.Fragment fragment = new ProjectTaskList();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("projectTitle",p.getProjectTitle());
        bundle.putInt("projectId", p.getDbId());

        ProjectTaskList projectTaskList = new ProjectTaskList();
        projectTaskList.setArguments(bundle);

        ft.replace(R.id.content_main2_frame_layout, projectTaskList);
        ft.commit();

        Log.d("NOTE: "," " + (itemIndex));

        /*
        Intent i = new Intent(getActivity(),ProjectTaskList.class);
        //id of the project to compare against tasks
        i.putExtra("projectId", p.getDbId());
        i.putExtra("projectTitle",p.getProjectTitle());
        startActivity(i);
        */


    }
    public void recyclerViewItems(){
        int i = 0;

        if(data.getCount() >  0){//only starts checking cursor for data if theres any data in the database
            //first attempt to solve cursor initialisation issue
            // data.moveToFirst();

            while(data.moveToNext()){
                Project project = new Project(data.getInt(0),data.getString(1),data.getLong(2),data.getLong(3));
                ProjectList.add(i,project);
                i++;
            }

        }else{
            Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
        }

    }
}
