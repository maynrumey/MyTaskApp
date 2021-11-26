package com.mayn.mytask.Entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "completed_task_table")
public class CompletedTask {

    @PrimaryKey(autoGenerate = true)
    public int cId;

    public String completedTitle;

    public String completedDescription;

    //Creating Constructor
    public CompletedTask(String completedTitle, String completedDescription) {
        this.completedTitle = completedTitle;
        this.completedDescription = completedDescription;
    }

    //Getter & setter

    public int getcId() {
        return cId;
    }

    public String getCompletedTitle() {
        return completedTitle;
    }

    public String getCompletedDescription() {
        return completedDescription;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }
}
