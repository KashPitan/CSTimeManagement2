package com.example.kash.cstimemanagement;

/**
 * Created by Kash on 24/02/2018.
 */

public class PTask {
    private String task;
    private String taskDetails;
    private int dbId;
    private int projectId;
    private boolean complete;
    private int urgency;
    private int importance;
    private int priority;
    private long dateCreated;
    private long dateDue;

    public PTask(String task, String taskDetails, int dbId, int projectId, int urgency, int importance, int priority, long dateCreated, long dateDue) {
        this.task = task;
        this.taskDetails = taskDetails;
        this.dbId = dbId;
        this.projectId = projectId;
        this.complete = false;
        this.urgency = urgency;
        this.importance = importance;
        this.priority = priority;
        this.dateCreated = dateCreated;
        this.dateDue = dateDue;
    }

    public String getTask() {
        return task;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public int getDbId() {
        return dbId;
    }

    public int getProjectId() {
        return projectId;
    }

    public boolean isComplete() {
        return complete;
    }

    public int getUrgency() {
        return urgency;
    }

    public int getImportance() {
        return importance;
    }

    public int getPriority() {
        return priority;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public long getDateDue() {
        return dateDue;
    }
}
