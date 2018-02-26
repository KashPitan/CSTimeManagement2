package com.example.kash.cstimemanagement;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Kash on 25/02/2018.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder>{

    private List<Project> ProjectList;
    private Context context;
    private Cursor mCursor;

    private clickListener mOnClickListener;

    public ProjectAdapter(List<Project> projectList, Context context, Cursor mCursor) {
        this.ProjectList = projectList;
        this.context = context;
        this.mCursor = mCursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        view = inflater.inflate(R.layout.project_list_item,parent,false);
        ViewHolder v = new ViewHolder(view);
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item,parent,false);

        return v;
    }

    @Override
    public void onBindViewHolder(ProjectAdapter.ViewHolder holder, int position) {
        Project Project = ProjectList.get(position);

        holder.tvHeader.setText(Project.getProjectTitle());
        holder.tvDueDetails.setText("Due: " + formatDate(Project.getDateDue(),"dd/MM/yyyy HH:mm"));

        //holder.projectIcon.setImageResource(priorityImage(Task));
        // int id = mCursor.getInt(mCursor.getColumnIndex("TASKID"));
        //holder.itemView.setTag(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        public TextView tvHeader;
        public TextView tvDueDetails;
        public ImageView projectIcon;

        public ViewHolder(View itemView) {
            super(itemView);

            tvDueDetails = (TextView)itemView.findViewById(R.id.project_list_item_due_details);
            tvHeader = (TextView)itemView.findViewById(R.id.project_item_header);
            projectIcon = (ImageView) itemView.findViewById(R.id.project_list_item_icon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
            // startActivity(new Intent(context,ViewTask.class));
            //Toast.makeText(context,"test ",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return ProjectList.size();
    }

    public void setClickListener(clickListener clickListener){
        this.mOnClickListener = clickListener;
    }

    public interface clickListener{
        void onListItemClick(int itemIndex);
    }

    public void removeFromRecycler(int itemIndex){
        ProjectList.remove(itemIndex );
        notifyItemRemoved(itemIndex);
    }

    public String formatDate(long milli, String format){
        DateFormat formatter = new SimpleDateFormat(format);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milli);
        return formatter.format(calendar.getTime());
    }

}
