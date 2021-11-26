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


public class TaskRepository {


    //--1 Creating object of taskDao
    private TaskDao taskDao;
    private CompletedDao completedDao;

    //--2 Creating Array of task & Completed Task - With LiveData
    private LiveData<List<Task>> taskList;
    private LiveData<List<CompletedTask>> completedTaskList;

    //--3 Creating a constructor of this class
    public TaskRepository(Application application){
        //Creating an object of task Database inside the constructor
        TaskDataBase database = TaskDataBase.getInstance(application);
        //Linking taskDao with database
        taskDao = database.taskDao();
        taskList = taskDao.getAllTask();

        completedDao = database.completedDao();
        completedTaskList = completedDao.getAllCompletedTask();

    }

    //--4. Calling operational methods
    public void Insert(Task task){

        //-->6. Execution of  AsyncTask class
        // In this insert method we need to create an Instance of "InsertNoteAsyncTask"

        new InsertTaskAsyncTask(taskDao).execute(task);

        //SO the insert method background task have been prepared
    }
    public void InsertCompletedTask(CompletedTask completedTask){

        new InsertCompletedTaskAsyncTask(completedDao).execute(completedTask);
    }

    public void Update(Task task){
        //-->6. Execution of  AsyncTask class

        new UpdateTaskAsyncTask(taskDao).execute(task);

    }
    public void Delete(Task task){
        //-->6. Execution of  AsyncTask class
        new DeleteTaskAsyncTask(taskDao).execute(task);

    }
    public void DeleteCompletedTask(CompletedTask completedTask){

        new DeleteCompletedTaskAsyncTask(completedDao).execute(completedTask);
    }

    public LiveData<List<Task>> getAllTask(){

        //this method return notes array
        return taskList;
    }

    public LiveData<List<CompletedTask>> getAllCompletedTask(){

        return completedTaskList;
    }


    //-- For Delete, Update , insert -operation we have to create a background trace by ourself
    //5- Creating ASynctask class for each operations and background task - synchronize task class for inside operation
    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void> {

        //We need to Define the NoteDao obejct in here inside this class
        private TaskDao taskDao;

        //Send NoteDao to the constructor of this class- as an argument
        private InsertTaskAsyncTask(TaskDao taskDao){

            this.taskDao= taskDao;
        }

        @Override
        protected Void doInBackground(Task... taskList) {

            taskDao.Insert(taskList[0]);

            return null;
        }
    }

    private static class InsertCompletedTaskAsyncTask extends AsyncTask<CompletedTask, Void, Void> {

        //We need to Define the NoteDao obejct in here inside this class
        private CompletedDao completedDao;

        //Send NoteDao to the constructor of this class- as an argument
        private InsertCompletedTaskAsyncTask(CompletedDao completedDao) {

            this.completedDao = completedDao;
        }

        @Override
        protected Void doInBackground(CompletedTask... completedTaskList) {

            completedDao.InsertCompletedTask(completedTaskList[0]);

            return null;
        }
    }

        //AsyncTask class for Update operation
    private static class UpdateTaskAsyncTask extends AsyncTask<Task, Void, Void>{

        //We need to Define the NoteDao obejct in here inside this class
        private TaskDao taskDao;

        //Send NoteDao to the constructor of this class- as an argument
        private UpdateTaskAsyncTask(TaskDao taskDao){

            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... taskList) {

            taskDao.Update(taskList[0]);

            return null;
        }
    }

    //Asynce Task class for Delete Operation
    //These Async Task class need to be executed on respective method
    private static class DeleteTaskAsyncTask extends AsyncTask<Task, Void, Void>{

        //We need to Define the NoteDao obejct in here inside this class
        private TaskDao taskDao;

        //Send NoteDao to the constructor of this class- as an argument
        private DeleteTaskAsyncTask(TaskDao taskDao){

            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... taskList) {

            taskDao.Delete(taskList[0]);

            return null;
        }

    }

    private static class DeleteCompletedTaskAsyncTask extends AsyncTask<CompletedTask, Void, Void>{

        //We need to Define the NoteDao obejct in here inside this class
        private CompletedDao completedDao;

        //Send NoteDao to the constructor of this class- as an argument
        private DeleteCompletedTaskAsyncTask(CompletedDao completedDao){

            this.completedDao = completedDao;
        }

        @Override
        protected Void doInBackground(CompletedTask... completedTaskList) {

            completedDao.DeleteCompletedTask(completedTaskList[0]);

            return null;
        }

    }


}
