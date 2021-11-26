package com.mayn.mytask.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mayn.mytask.Entity.CompletedTask;
import com.mayn.mytask.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class CompletedTaskAdapter extends RecyclerView.Adapter<CompletedTaskAdapter.CompletedTaskHolder> {

    private List<CompletedTask> completedTaskList = new ArrayList<>();

    @NonNull
    @Override
    public CompletedTaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_task_design,parent,false);

        return new CompletedTaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedTaskHolder holder, int position) {

            CompletedTask currentCompletedTask = completedTaskList.get(position);
            holder.completedTaskTitle.setText(currentCompletedTask.getCompletedTitle());
            holder.completedTaskDescription.setText(currentCompletedTask.getCompletedDescription());

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

        public CompletedTaskHolder(@NonNull View itemView) {
            super(itemView);

            completedTaskTitle = itemView.findViewById(R.id.textViewCompletedTitle);
            completedTaskDescription = itemView.findViewById(R.id.textViewCompletedDescription);




        }
    }

}
