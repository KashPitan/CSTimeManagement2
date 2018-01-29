package com.example.kash.cstimemanagement;

/**
 * Created by Kash on 29/12/2017.
 */

public class Task {
    private String task;
    private String taskDetails;

    public Task(String task, String taskDetails) {
        this.task = task;
        this.taskDetails = taskDetails;
    }

    public String getTask() {
        return task;
    }

    public String getTaskDetails() {
        return taskDetails;
    }
}
