package com.example.kash.cstimemanagement;

/**
 * Created by Kash on 29/12/2017.
 */

public class Task {
    private String task;
    private String taskDetails;
    private int dbId;
    private boolean complete;

    public Task(String task, String taskDetails,int dbId) {
        this.task = task;
        this.taskDetails = taskDetails;
        this.dbId = dbId;
        this.complete = false;
    }

    public String getTask() {
        return task;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public int getTaskDBId(){return dbId;}

    public boolean isComplete(){return complete;}
}
