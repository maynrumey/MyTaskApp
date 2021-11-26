package com.mayn.mytask.Entity;



//1.
//Giving Entity annotation - Room will create table automatecly
//If we want multiple table on database - Need to create model class with multiple model class with Entity annotation

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task {

    //.2
    // Now-  create column name of table by defining variables - And room db automatically create table column
    //We will create two column  "task title" and task description & Date and Time
    //-Also note will be stored in database with an Id number
    //SO , we need a ID column - id need to be unique- to make it unique - We need define id as "@Primary Key"
    //"autoGenerate = true" -- Making it true - now system automatically create id
    @PrimaryKey(autoGenerate = true)
    public int id;

    public  String title;

    public String description;


    //->3
    //Creating constructor for model class
    //We didn't create constructor for ID beacuse - id will generate automatically
    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    //-->4
    //Getter method - will create with all variable

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    //-->5
    //Generate Setter for Id only


    public void setId(int id) {
        this.id = id;
    }

}
