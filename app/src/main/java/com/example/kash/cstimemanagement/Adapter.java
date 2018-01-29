package com.example.kash.cstimemanagement;

/**
 * Created by Kash on 28/12/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private List<Task> TaskList;
    private Context context;

    public Adapter(List<Task> taskList, Context context) {
        this.TaskList = taskList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        view = inflater.inflate(R.layout.task_list_item,null);
        ViewHolder v = new ViewHolder(view);
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item,parent,false);

        return v;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task Task = TaskList.get(position);

        holder.tvHeader.setText(Task.getTask());
        holder.tvDetails.setText(Task.getTaskDetails());
    }

    @Override
    public int getItemCount() {
        return TaskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvHeader;
        public TextView tvDetails;

        public ViewHolder(View itemView) {
            super(itemView);

            tvHeader = (TextView)itemView.findViewById(R.id.item_header);
            tvDetails = (TextView)itemView.findViewById(R.id.item_details);
        }

        void bind(int listIndex){

        }
    }
}
