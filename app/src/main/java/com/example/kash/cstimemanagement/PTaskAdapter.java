package com.example.kash.cstimemanagement;

/**
 * Created by Kash on 28/12/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class PTaskAdapter extends RecyclerView.Adapter<PTaskAdapter.ViewHolder>{

    private List<PTask> PTaskList;
    private Context context;
    private Cursor mCursor;

    private clickListener mOnClickListener;

    public PTaskAdapter(List<PTask> PTaskList, Context context, Cursor cursor) {
        this.PTaskList = PTaskList;
        this.context = context;
        this.mCursor = cursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        view = inflater.inflate(R.layout.task_list_item,parent,false);
        ViewHolder v = new ViewHolder(view);
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item,parent,false);

        return v;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PTask PTask = PTaskList.get(position);

        holder.tvHeader.setText(PTask.getTask());
        holder.tvDetails.setText(PTask.getTaskDetails());
        holder.tvDueDetails.setText("Due: " + formatDate(PTask.getDateDue(),"dd/MM/yyyy HH:mm"));
        holder.priorityIcon.setImageResource(priorityImage(PTask));
        // int id = mCursor.getInt(mCursor.getColumnIndex("TASKID"));
        //holder.itemView.setTag(id);
    }

    public int priorityImage(PTask ptask){
        int priorityImageId;
        Log.d("Priority", "" + ptask.getPriority());
        switch(ptask.getPriority()){
            case 1: priorityImageId = R.drawable.priority_level_1;
                break;
            case 2: priorityImageId = R.drawable.priority_level_2;
                break;
            case 3: priorityImageId = R.drawable.priority_level_3;
                break;
            case 4: priorityImageId = R.drawable.priority_level_4;
                break;
            default: priorityImageId = R.drawable.priority_level_4;
                break;
        }
        return priorityImageId;
    }

    @Override
    public int getItemCount() {
        //return mCursor.getCount;
        return PTaskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        public TextView tvHeader;
        public TextView tvDetails;
        public TextView tvDueDetails;
        public ImageView priorityIcon;

        public ViewHolder(View itemView) {
            super(itemView);

            tvDueDetails = (TextView)itemView.findViewById(R.id.task_list_item_due_details);
            tvHeader = (TextView)itemView.findViewById(R.id.item_header);
            tvDetails = (TextView)itemView.findViewById(R.id.item_details);
            priorityIcon = (ImageView) itemView.findViewById(R.id.task_list_item_priority_icon);

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
    public int getItemViewType(int position) {


        return super.getItemViewType(position);
    }

    public void setClickListener(clickListener clickListener){
        this.mOnClickListener = clickListener;
    }

    public interface clickListener{
        void onListItemClick(int itemIndex);
    }

    public void removeFromRecycler(int itemIndex){
        PTaskList.remove(itemIndex );
        notifyItemRemoved(itemIndex);
    }

    public String formatDate(long milli, String format){
        DateFormat formatter = new SimpleDateFormat(format);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milli);
        return formatter.format(calendar.getTime());
    }

}
