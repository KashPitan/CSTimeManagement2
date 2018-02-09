package com.example.kash.cstimemanagement;

/**
 * Created by Kash on 29/12/2017.
 */

public class Task {
    private String task;
    private String taskDetails;
    private int dbId;
    private boolean complete;
    private int urgency;
    private int importance;
    private int priority;

    public Task(String task, String taskDetails,int dbId, int urgency, int importance,int priority) {
        this.task = task;
        this.taskDetails = taskDetails;
        this.dbId = dbId;
        this.complete = false;
        this.urgency = urgency;
        this.importance = importance;
        this.priority = priority;

    }

    public String getTask() {
        return task;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public int getTaskDBId(){return dbId;}

    public boolean isComplete(){return complete;}

    public boolean isUrgent(){
        if(this.urgency == 1){
            return true;
        }else{
            return false;
        }
    }

    public boolean isImportant(){
        if(this.importance == 1){
            return true;
        }else{
            return false;
        }
    }
    public int getPriority(){return priority;}
}
