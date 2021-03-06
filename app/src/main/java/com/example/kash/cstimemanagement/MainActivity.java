package com.example.kash.cstimemanagement;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.clickListener{

    DBHelper db;
    private List<Task> taskList;
    private List<Task> completedTaskList;

    //list used to display tasks
    private List<Task> displayList;

    private RecyclerView recyclerView;

    private Cursor data;

    private Adapter mAdapter;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    FloatingActionButton add_task_button;

    private ListView mDrawerList;
    private ArrayAdapter<String> mArrayAdapter;

    private TextView subheading;
    private String subheadingString;

    private ImageView noDataImage;
    private TextView noDataText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noDataImage = (ImageView)findViewById(R.id.activity_main_no_data_image);
        noDataText = (TextView)findViewById(R.id.activity_main_no_data_text);

        subheading = (TextView)findViewById(R.id.activity_main_subheader);
        subheadingString = "All Tasks";
        subheading.setText(subheadingString);

        mDrawerList = (ListView)findViewById(R.id.activity_main_navigation_view_list_view);
        addDrawerItems();

        mDrawerLayout = (DrawerLayout)findViewById(R.id.activity_main_drawer_layout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.Drawer_open,R.string.Drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        //second method for solving cursor initialization issue
        //data = ;

        //retrieve data from database and store in Cursor object
        data = db.getData(-1);
        //Toast.makeText(MainActivity.this, "N " + System.currentTimeMillis(), Toast.LENGTH_SHORT).show();


        //create list to hold task objects
       //taskList = new ArrayList<>();
        displayList = new ArrayList<>();
        recyclerViewItems();


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    //all tasks
                    case 0:
                        //Toast.makeText(MainActivity.this, "All tasks", Toast.LENGTH_SHORT).show();
                        clearList();
                        recyclerViewItems();
                        data = db.getData(-1);
                        recyclerViewItems();
                        subheading.setText("All Tasks");
                        mDrawerLayout.closeDrawers();
                        break;
                    //completed tasks
                    case 1:
                        //Toast.makeText(MainActivity.this, "Completed Tasks", Toast.LENGTH_SHORT).show();
                        clearList();
                        recyclerViewItems();
                        data = db.getCompleteData(-1);
                        recyclerViewItems();
                        subheading.setText("Completed Tasks");
                        mDrawerLayout.closeDrawers();
                        break;
                     //incomplete tasks
                    case 2:
                        //Toast.makeText(MainActivity.this, "Incomplete Tasks", Toast.LENGTH_SHORT).show();
                        clearList();
                        recyclerViewItems();
                        data = db.getInCompleteTaskData(-1);
                        recyclerViewItems();
                        subheading.setText("Incomplete Tasks");
                        mDrawerLayout.closeDrawers();
                        break;
                    //prioritised tasks
                    case 3:
                        //Toast.makeText(MainActivity.this, "Prioritised Tasks", Toast.LENGTH_SHORT).show();
                        clearList();
                        recyclerViewItems();
                        data = db.getPrioritisedTaskData(-1);
                        recyclerViewItems();
                        subheading.setText("Prioritised Tasks");
                        mDrawerLayout.closeDrawers();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "No data", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });

        //handling swiping
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                Task t = displayList.get(position);
                int index = t.getTaskDBId();
                Log.d("NOTE: "," " + (position));
                db.setToComplete(index);
                mAdapter.removeFromRecycler(position);

            }
        };
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    //clears the list
    public void clearList(){
        displayList.clear();
    }

    //refresh items in recycler view
    public void recyclerViewItems(){
        int i = 0;

        if(data.getCount() >  0){//only starts checking cursor for data if theres any data in the database
            //first attempt to solve cursor initialisation issue
           // data.moveToFirst();

            noDataImage.setImageResource(0);
            noDataText.setText("");

            while(data.moveToNext()){
                Task task = new Task(data.getString(1),data.getString(2),data.getInt(0),data.getInt(4),data.getInt(5),data.getInt(6),data.getLong(7),data.getLong(8),-1);
                displayList.add(i,task);
                i++;
            }

            mAdapter = new Adapter(displayList,this,data);
            mAdapter.setClickListener(this);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        }else{
            noDataImage.setImageResource(R.drawable.splashscreenv2flatpurple);
            noDataText.setText("No Tasks!");
            //Toast.makeText(MainActivity.this, "No data", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onListItemClick(int itemIndex) {
        //Toast.makeText(MainActivity.this, "click test " + itemIndex, Toast.LENGTH_SHORT).show();
        Task t = displayList.get(itemIndex);
        Log.d("NOTE: "," " + (itemIndex));

       //Toast.makeText(MainActivity.this, t.getTask() + " " + t.getTaskDetails() + " " + t.getTaskDBId() , Toast.LENGTH_SHORT).show();
       // Toast.makeText(MainActivity.this,t.isImportant() + " " + t.isUrgent(),Toast.LENGTH_SHORT).show();
        //Toast.makeText(MainActivity.this,t.getPriority() + "",Toast.LENGTH_SHORT).show();
        //Toast.makeText(MainActivity.this,t.getDateCreated() + "",Toast.LENGTH_SHORT).show();


        Intent i = new Intent(this,ViewTask.class);
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

    private void addDrawerItems(){
        String[] arr_itemNames = {"All Tasks","Completed Tasks","Incomplete Tasks", "Prioritised Tasks"};
        mArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arr_itemNames);
        mDrawerList.setAdapter(mArrayAdapter);
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

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
