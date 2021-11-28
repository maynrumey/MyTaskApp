package com.mayn.mytask.Fragment;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mayn.mytask.Adapter.TaskAdapter;
import com.mayn.mytask.AddTaskActivity;
import com.mayn.mytask.EditTaskActivity;
import com.mayn.mytask.Entity.CompletedTask;
import com.mayn.mytask.Entity.Task;
import com.mayn.mytask.R;
import com.mayn.mytask.ViewModel.TaskViewModel;

import java.util.List;
import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class TaskFragment extends Fragment {


    public TaskFragment() {

    }


    public static TaskFragment newInstance(){

        return new TaskFragment();
    }


    //1.Create an Object of View ModelClass
    private TaskViewModel taskViewModel;


    FloatingActionButton addTaskFAB;

    RecyclerView recyclerView;

    TaskAdapter adapter;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.task_fragment, container, false);

        recyclerView = view.findViewById(R.id.taskRecyclerView);
        addTaskFAB = view.findViewById(R.id.floating_action_button);
        //Setting layout manager to recycler view

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        //To reverse the Recycler View list
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireActivity().getApplication(), LinearLayoutManager.VERTICAL ));

        //Create an Object of TaskAdapter Class
        adapter = new TaskAdapter(requireActivity());
        //Assign noteAdapter object to recyclerView
        recyclerView.setAdapter(adapter);

        //2.Assinging view model inside this OncreateView


        taskViewModel = new ViewModelProvider.AndroidViewModelFactory(
                requireActivity().getApplication()).create(TaskViewModel.class);

        //3.Writing codes get notes data from view model

        taskViewModel.getAllTask().observe(requireActivity(), new
                Observer<List<Task>>() {
                    @Override
                    public void onChanged(List<Task> taskList) {

                        adapter.setTaskList(taskList);

                    }
                });




        //------->Codes for Delete Task on swiped---------->

        //We need call itemTouchHelper method with SimpleCallBack
        //In parameter we need to give direction of swiped
        //Also we have created a SetTask method on Adapter to Update Delete data on arraylist
        //to get task position - we have created a getTask method on Adapter class

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT ){
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
                //This method is used for drag and drop recycler item

            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                String completedTaskTile = adapter.getTask(viewHolder.getAdapterPosition()).getTitle();
                String completedTaskDescription = adapter.getTask(viewHolder.getAdapterPosition()).getDescription();
                String completedTaskTime = adapter.getTask(viewHolder.getAdapterPosition()).getTime();

                int completedTaskId = adapter.getTask(viewHolder.getAdapterPosition()).getId();

                CompletedTask completedDeleteTask = new CompletedTask(completedTaskTile, completedTaskDescription);
                taskViewModel.InsertCompletedTask(completedDeleteTask);

                taskViewModel.Delete(adapter.getTask(viewHolder.getAdapterPosition()));

                Snackbar.make(recyclerView, "Task Completed", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Task task = new Task(completedTaskTile,completedTaskDescription, completedTaskTime);
                                task.setId(completedTaskId);
                                taskViewModel.Insert(task);

                            }
                        }).show();



            }

            //--->Decorative Swipe---->

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                        .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(), R.color.teal_700))
                        .addSwipeRightActionIcon(R.drawable.ic_baseline_archive_24)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            //--> Connect Item Touch helper with recycler View
        }).attachToRecyclerView(recyclerView);





        //SetOnclickListner on for
        addTaskFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddTaskActivity();
            }
        });




//        Calling "openEditTaskActivity()" to open Task

        openEditTaskActivity();





        return view;
    }



    //----->Getting AddTaskActivity data on this Fragment by Activity result launcher----->
    //--> Add this Floating Button click Listener intent

    ActivityResultLauncher<Intent> launchAddTaskActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == AddTaskActivity.RESULT_OK){
                        Intent data = result.getData();

                        //Importing data from addNote Activity to string container
                        assert data != null;
                        String title = data.getStringExtra("title");
                        String description = data.getStringExtra("description");
                        String time = data.getStringExtra("time");

                        //-->Adding these data to Database-->
                        // We need to create an Object of Note class and passing - note and description data container
                        Task task = new Task(title, description, time);

                        //--Now adding this "note" object to DataBase through ViewModel Object and Insert Method
                        taskViewModel.Insert(task);

                    }
                }
            });

        public void openAddTaskActivity(){
        Intent intent = new Intent(getContext(), AddTaskActivity.class);
        launchAddTaskActivity.launch(intent);
    }


    //----> Creating Intent for EditTask ---> IntentforResult - for Edit Task Activity--To send and received data
       ActivityResultLauncher<Intent> launchEditTaskActivity = registerForActivityResult
            (new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == EditTaskActivity.RESULT_OK){
                        Intent data = result.getData();

                        //Importing data from EditNote Activity to string container
                        assert data != null;
                        String title = data.getStringExtra("title_update");
                        String description = data.getStringExtra("description_update");
                        String newTime = data.getStringExtra("new_time");
                        int id = data.getIntExtra("task_id",-1);

                        //-->Updating database with these Data-->
                        // We need to create an Object of Task class and passing - Task and description data container
                        Task task = new Task(title, description, newTime);
                        task.setId(id);

                        //--Now adding this "note" object to DataBase through ViewModel Object and Insert Method
                        taskViewModel.Update(task);

                    }

                }
            });

        public void openEditTaskActivity(){

            //-----Set Item Click Listener for Task---->
            //Send Data to EditTaskFragment
            adapter.setOnTaskClickListener(new TaskAdapter.OnTaskClickListener() {
                @Override
                public void onTaskClick(Task task) {
                    //Create Intent to click on Notes
                    Intent intent = new Intent(getContext(), EditTaskActivity.class);
                    //We also need to send the current Note data to "EditNoteActivity"
                    intent.putExtra("id", task.getId());
                    intent.putExtra("task_title", task.getTitle());
                    intent.putExtra("task_description", task.getDescription());
                    launchEditTaskActivity.launch(intent);
                }
            });
        }


}
