package com.mayn.mytask.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.mayn.mytask.Dao.CompletedDao;
import com.mayn.mytask.Entity.CompletedTask;
import com.mayn.mytask.Entity.Task;
import com.mayn.mytask.Dao.TaskDao;
import com.mayn.mytask.Database.TaskDataBase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TaskRepository {


    //--1 Creating object of taskDao
    private TaskDao taskDao;
    private CompletedDao completedDao;

    //--2 Creating Array of task & Completed Task - With LiveData
    private LiveData<List<Task>> taskList;
    private LiveData<List<CompletedTask>> completedTaskList;

    //Calling Executor service
    ExecutorService executors = Executors.newSingleThreadExecutor();

    //--3 Creating a constructor of repository class
    public TaskRepository(Application application){

        //Creating an object of task Database inside the constructor
        TaskDataBase database = TaskDataBase.getInstance(application);
        //Linking taskDao with database
        taskDao = database.taskDao();
        taskList = taskDao.getAllTask();

        completedDao = database.completedDao();
        completedTaskList = completedDao.getAllCompletedTask();

    }

    //--4. Calling operational methods --> Use Executor services to insert task using Dao
    public void Insert(Task task){

        executors.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.Insert(task);
            }
        });

    }
    public void InsertCompletedTask(CompletedTask completedTask){

        executors.execute(new Runnable() {
            @Override
            public void run() {
                completedDao.InsertCompletedTask(completedTask);
            }
        });

    }

    public void Update(Task task){

        executors.execute(new Runnable() {
            @Override
            public void run() {

                taskDao.Update(task);
            }
        });

    }
    public void Delete(Task task){

        executors.execute(new Runnable() {
            @Override
            public void run() {

                taskDao.Delete(task);
            }
        });

    }

    public void DeleteCompletedTask(CompletedTask completedTask){

        executors.execute(new Runnable() {
            @Override
            public void run() {

                completedDao.DeleteCompletedTask(completedTask);

            }
        });
    }

    public LiveData<List<Task>> getAllTask(){

        //this method return notes array
        return taskList;
    }

    public LiveData<List<CompletedTask>> getAllCompletedTask(){

        return completedTaskList;
    }




}
