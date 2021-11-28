package com.mayn.mytask.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.mayn.mytask.Entity.CompletedTask;
import com.mayn.mytask.R;
import com.mayn.mytask.ViewModel.TaskViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class CompletedTaskAdapter extends RecyclerView.Adapter<CompletedTaskAdapter.CompletedTaskHolder> {

    private List<CompletedTask> completedTaskList = new ArrayList<>();

    //ViewBinderHelper Object for SwipeReavleLayout
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    Application application;

//    public CompletedTaskAdapter( Application application){
//
//        this.application = application;
//    }

    private TaskViewModel taskViewModel;

//    private TaskViewModel taskViewModel = (TaskViewModel) new ViewModelProvider.AndroidViewModelFactory(application)
//            .create(TaskViewModel.class);

    @NonNull
    @Override
    public CompletedTaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_task_design,parent,false);

        return new CompletedTaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedTaskHolder holder, int position) {

            CompletedTask currentCompletedTask = completedTaskList.get(position);

            //- SwipeReavleLayout - if you want to open only one row at a time
            viewBinderHelper.setOpenOnlyOne(true);
            //Binding swipereveallayout with view Bind Helper
            viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(currentCompletedTask.getcId()));
            viewBinderHelper.closeLayout(String.valueOf(currentCompletedTask.getcId()));



            holder.completedTaskTitle.setText(currentCompletedTask.getCompletedTitle());
            holder.completedTaskDescription.setText(currentCompletedTask.getCompletedDescription());

            holder.deleteButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    taskViewModel.DeleteCompletedTask(getCompletedTask(holder.getAdapterPosition()));

                }
            });

    }

    @Override
    public int getItemCount() {
        return completedTaskList.size();
    }

    //---> Create an Extra SetTask method to Update data on arraylist
    @SuppressLint("NotifyDataSetChanged")
    public void setCompletedTaskList(List<CompletedTask> completedTaskList){

        this.completedTaskList = completedTaskList;
        notifyDataSetChanged();
    }


    //---->Creating a getNote Method with Position parameter to get Note position
    // THis method will get position of notes from Array
    public CompletedTask getCompletedTask(int position){
        return completedTaskList.get(position);
    }

    static class CompletedTaskHolder extends RecyclerView.ViewHolder{

        TextView completedTaskTitle, completedTaskDescription;
        TextView deleteButton;
        SwipeRevealLayout swipeRevealLayout;

        public CompletedTaskHolder(@NonNull View itemView) {
            super(itemView);

            completedTaskTitle = itemView.findViewById(R.id.textViewCompletedTitle);
            completedTaskDescription = itemView.findViewById(R.id.textViewCompletedDescription);
            deleteButton = itemView.findViewById(R.id.textDelete);
            swipeRevealLayout = itemView.findViewById(R.id.swiperevealLayout);




        }
    }

}
