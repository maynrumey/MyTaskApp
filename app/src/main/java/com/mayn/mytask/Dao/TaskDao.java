package com.mayn.mytask.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mayn.mytask.Entity.Task;

import java.util.List;

//->1. Annotate the interface With Dao
@Dao
public interface TaskDao {

    //Interface only takes method without interface
    //Now we will create Methods for operation like Add, update and delete data in Database

    //->2 We write insert method and annotate with Insert
    @Insert
    void Insert(Task task);

    //->3 We write insert method and annotate with @Update
    @Update
    void Update(Task task);

    //->4 We write insert method and annotate with @Delete
    @Delete
    void Delete(Task task);



    //-->5-Finally- We will generate a quire operation
    //- We will use the quire to list the note in database
    //Annotate the process quire
    //Here "SELECT *" *- means all note, "This select will be Order by Id ASC"
    //"ASC" - Means Ascender- List the note small to large - when compare to Id
    @Query("SELECT * FROM task_table ORDER BY id ASC")
    //Return type and  method of query to get All notes
    //Also we will add Live data components in mehtod - So it will absolve live data - database and reflect it if there is any change
    //Here "List<Note>" is an array return type
    LiveData<List<Task>> getAllTask();


}
