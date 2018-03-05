package com.example.kash.cstimemanagement;

/**
 * Created by Kash on 24/02/2018.
 */

public class Project {
    private int dbId;
    private String ProjectTitle;
    private long dateDue;
    private long dateCreated;
    private boolean complete;

    public Project(int dbId, String projectTitle, long dateDue, long dateCreated) {
        this.dbId = dbId;
        ProjectTitle = projectTitle;
        this.dateDue = dateDue;
        this.dateCreated = dateCreated;
        this.complete = false;
    }

    public boolean isComplete(){return complete;}

    public int getDbId() {
        return dbId;
    }

    public String getProjectTitle() {
        return ProjectTitle;
    }

    public long getDateDue() {
        return dateDue;
    }

    public long getDateCreated() {
        return dateCreated;
    }
}
