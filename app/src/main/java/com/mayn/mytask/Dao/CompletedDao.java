package com.mayn.mytask.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mayn.mytask.Entity.CompletedTask;

import java.util.List;

@Dao
public interface CompletedDao {


    //Methods for Operation

    @Insert
    void InsertCompletedTask(CompletedTask completedTask);

    @Delete
    void DeleteCompletedTask(CompletedTask completedTask);

    //Quire Operation with Live Data
    @Query("SELECT * FROM completed_task_table ORDER BY cId ASC")
    LiveData<List<CompletedTask>> getAllCompletedTask();


}
