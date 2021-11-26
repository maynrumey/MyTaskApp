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

//This is RoomDataBase class
//-1. This class will extends with RoomDatabase class
//This Database class will be abstract class
// Class need to annotate with @DataBase entity
//Here-In the annotation "entities = Note.class" link the note class with database
//If we have multiple note table or entity- need to linked in here


@Database(
        version = 4,
        entities = {Task.class, CompletedTask.class})
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




    //Latter task - Additional
    //---> Put some data to Blank Database---so by default--so open the these data will show------>
    //--Integrate this callback method on Database builder --before .build();
    //-->1. Write a new method
    private static RoomDatabase.Callback roomCallback = new Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            //-->6 Executing Async task under onCreate method
            new PopulateDbAsyncTask(instance).execute();

            //--Finally, We have to integrate this call back method into Database Builder.
        }
    };

    //--->2. Create an Async task class
    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {

        //-->3.Calling TaskDao Object in here
        private TaskDao taskDao;
        private CompletedDao completedDao;

        //--->4.Creating constructor of this class with Sending TaskData base object to constructor
        private PopulateDbAsyncTask(TaskDataBase database) {

            taskDao = database.taskDao();
            completedDao = database.completedDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {

//            //--5. now we can add noted to database by making this insert method
//            taskDao.Insert(new Task("Title1", "Description1"));
//
//            taskDao.Insert(new Task("Title2", "Description2"));

//            //------>Also we need to run this "AsyncTask" on OnCreate method

            return null;
        }

    }


}
