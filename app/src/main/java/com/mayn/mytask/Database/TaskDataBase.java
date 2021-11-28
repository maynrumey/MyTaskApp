package com.mayn.mytask.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mayn.mytask.Dao.CompletedDao;
import com.mayn.mytask.Entity.CompletedTask;
import com.mayn.mytask.Entity.Task;
import com.mayn.mytask.Dao.TaskDao;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//This is RoomDataBase class
//-1. This class will extends with RoomDatabase class
//This Database class will be abstract class
// Class need to annotate with @DataBase entity
//Here-In the annotation "entities = Note.class" link the note class with database
//If we have multiple note table or entity- need to linked in here


@Database(entities = {Task.class,CompletedTask.class},version = 5)
public abstract class TaskDataBase extends RoomDatabase {

    //-2 Creating a TaskDatabase instance object - We will use this database object on everywhere in the application
    private static TaskDataBase instance;

    //--3 Create an Abstract method With DAO
    public abstract TaskDao taskDao();
    public abstract CompletedDao completedDao();

    //--4 Write methods required for database

    public static synchronized TaskDataBase getInstance(Context context){
        //Need to check - instance is null or not

        if (instance == null){

            instance = Room.databaseBuilder(context.getApplicationContext()
                    ,TaskDataBase.class, "task_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();

        }

        return instance;
    }

    //---> Used Thread Executor for roomCallback
    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            TaskDao taskDao = instance.taskDao();

            ExecutorService executorService = Executors.newSingleThreadExecutor();

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    ///Insert task to database
                }
            });



        }
    };



}
