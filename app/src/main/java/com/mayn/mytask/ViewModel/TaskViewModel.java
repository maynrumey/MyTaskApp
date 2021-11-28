package com.mayn.mytask.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mayn.mytask.Entity.CompletedTask;
import com.mayn.mytask.Repository.TaskRepository;
import com.mayn.mytask.Entity.Task;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    //--1. We need define Repository and create an Object
    private TaskRepository repository;

    //--2. Creating an array containing all notes-- with Livedata
    private LiveData<List<Task>> taskList;
    private LiveData<List<CompletedTask>> completedTaskList;


    //.Making constructor of view model
    ///--3. Define above elements into constructor


    public TaskViewModel(@NonNull Application application) {
        super(application);

        repository = new TaskRepository(application);

        taskList = repository.getAllTask();
        completedTaskList = repository.getAllCompletedTask();
    }

    //--4. Now we need call the Operation methods that we perform in the database

    public void Insert(Task task){

        repository.Insert(task);

    }
    public void InsertCompletedTask(CompletedTask completedTask){

        repository.InsertCompletedTask(completedTask);
    }

    public void Update(Task task){

        repository.Update(task);
    }

    public void Delete(Task task){

        repository.Delete(task);
    }
    public void DeleteCompletedTask(CompletedTask completedTask){

        repository.DeleteCompletedTask(completedTask);
    }

    //-Finally call the GetallTask to return Task Arraylist
    public LiveData<List<Task>> getAllTask(){

        return taskList;
    }

    public LiveData<List<CompletedTask>> getAllCompletedTask(){

        return completedTaskList;
    }

    /////------>We should show this view model class as reference in the Main Activity ----->

}
