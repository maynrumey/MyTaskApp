package com.mayn.mytask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class AddTaskActivity extends AppCompatActivity {

    private EditText addTaskTitle, addTaskDescription;
    private TextView dateTime;
    private Button buttonSave, buttonCancel;

    Toolbar taskToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        taskToolbar = findViewById(R.id.taskToolbar);
        setSupportActionBar(taskToolbar);
        getSupportActionBar().setTitle("Add Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        addTaskTitle = findViewById(R.id.editTextTitle);
        addTaskDescription = findViewById(R.id.editTextDescription);
        buttonSave = findViewById(R.id.buttonSave);
        buttonCancel = findViewById(R.id.buttonCancel);
        dateTime = findViewById(R.id.textViewAddDate);

        //Click listener for Buttons

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //We clicking this button we will save notes to DB-
                //We are creating a Save notes on outside and calling the methods in here
                saveTask();

            }
        });



    }

    public void saveTask(){

        //1. Getting user's edit text Notes on String container
        String taskTitle = addTaskTitle.getText().toString();
        String taskDescription = addTaskDescription.getText().toString();

        SimpleDateFormat timeStamp = new SimpleDateFormat("dd MMM, yyyy - HH:mm");
        String currentDate = timeStamp.format(new Date());
        //In here we will send the user data to Main Activity and save it to datdbase from Main Activity
        //Sending data to Main Activity using Intent
        Intent i = new Intent();
        i.putExtra("title", taskTitle);
        i.putExtra("description",taskDescription);
        i.putExtra("time",currentDate);
        setResult(RESULT_OK,i);
        finish();

    }

    //TO Enable TO-Back Action Button, We need call below method

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}