package com.mayn.mytask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditTaskActivity extends AppCompatActivity {

    EditText editTitle, editDescription;
    TextView editDate;
    Button editSave, editCancel;

    int taskId;

    Toolbar taskToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        //Set Toolbar
        taskToolbar = findViewById(R.id.taskToolbar);
        setSupportActionBar(taskToolbar);
        getSupportActionBar().setTitle("Edit Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTitle = findViewById(R.id.editTextEditTitle);
        editDescription = findViewById(R.id.editTextEditDescription);

        editSave = findViewById(R.id.editButtonSave);
        editCancel = findViewById(R.id.editButtonCancel);

        //First we need to get Data from Fragment using intent
        getData();

        //Click Listener for Button
        editCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });
        editSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //We clicking this button we will update notes to DB-
                //We are creating a updatenotes methods on outside and calling the methods in here
                updateTask();

            }
        });



    }

    //Creating UpdateNote method
    public void updateTask(){

        // We will send the Update Task Data to Fragment - save to DB from Fragment
        String titleLast = editTitle.getText().toString();
        String descriptionLast = editDescription.getText().toString();

        //Using intent to send Data
        Intent intent = new Intent();

        intent.putExtra("title_update", titleLast);
        intent.putExtra("description_update",descriptionLast);

        //Sending task Id to update specific task - we need use If condition
        if (taskId != -1){
            intent.putExtra("task_id",taskId);
        }
        setResult(RESULT_OK,intent);
        finish();
    }

    public void getData(){

        Intent i  = getIntent();
        taskId = i.getIntExtra("id", -1);
        String title = i.getStringExtra("task_title");
        String description = i.getStringExtra("task_description");

        //Setting this "title" & "description" to editTitle & editDescription
        editTitle.setText(title);
        editDescription.setText(description);
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