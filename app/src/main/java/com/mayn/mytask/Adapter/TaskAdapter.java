package com.mayn.mytask.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.google.android.material.snackbar.Snackbar;
import com.mayn.mytask.EditTaskActivity;
import com.mayn.mytask.Entity.CompletedTask;
import com.mayn.mytask.Entity.Task;
import com.mayn.mytask.Fragment.TaskFragment;
import com.mayn.mytask.R;
import com.mayn.mytask.ViewModel.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{

    //3. Create ArrayList of notes
    private List<Task> taskList = new ArrayList<>();

    Activity activity;

    public TaskAdapter(Activity activity){

        this.activity= activity;
    }


    //-->Creating Object Of "ItemClick Lisntener" for itemClick on task that created below
    private OnTaskClickListener listener;


    //Default Super methods
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //4. Inflate Card view with View class object
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.taskview_design, parent, false);


        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Task currentTask = taskList.get(position);



        //5. In this section, we will transfer the data from java object to note holder
        //Each note come to us from Database as a java object- to this title and description
        //--->Determine the position of with creating cuurentNote object from Note class
        // Assigning note Title and description to the View

        holder.taskTitle.setText(currentTask.getTitle());
        holder.taskDescription.setText(currentTask.getDescription());
        holder.taskDate.setText(currentTask.getTime());



    }

    @Override
    public int getItemCount() {

        return taskList.size();
    }

    //---> We need to create an Extra SetTask method to Update data on arraylist - to alert Dataset Changes on task Arraylist
    //This will alert the adapter if there is data change in dataBase
    @SuppressLint("NotifyDataSetChanged")
    public void setTaskList(List<Task> taskList){

        this.taskList = taskList;
        notifyDataSetChanged();
    }

    //---->We are creating a getNote Method with Position parameter to get Note position
    //--This method will need to Delete notes
    // THis method will get position of notes from Array
    public Task getTask(int position){
        return taskList.get(position);
    }


    //2.Creating Holder class and define UI

    public class TaskViewHolder extends RecyclerView.ViewHolder{

        //Defining CardView item In this holder class
        TextView taskTitle, taskDescription, taskDate;

        //Define SwipeReveale layout
        TextView deleteButton, completeButton;
        SwipeRevealLayout swipeRevealLayout;
        CardView cardView;


        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            taskTitle = itemView.findViewById(R.id.textTitle);
            taskDescription = itemView.findViewById(R.id.textDescription);
            taskDate = itemView.findViewById(R.id.textViewDnT);




            //Setting OnClickListener for CardView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //to Determine the Note Position- create an Int Variable
                    int position = getAdapterPosition();
                    //Now we run if condition-if Listener & position is not Null
                    if (listener != null && position != RecyclerView.NO_POSITION){

                        listener.onTaskClick(taskList.get(position));

                    }

                }
            });

        }



    }

    //---------->To enable ClickListener for Task to Update and edit------>>
    //Create and interface of taskClickListener--
    //And create a method inside interface with Note parameter
    //Create Method named setOnclick Listener
    public interface OnTaskClickListener{

        void onTaskClick(Task task);

    }
    public void setOnTaskClickListener(OnTaskClickListener listener){

        this.listener = listener;

    }



}
