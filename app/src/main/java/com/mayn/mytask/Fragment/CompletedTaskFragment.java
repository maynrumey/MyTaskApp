package com.mayn.mytask.Fragment;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mayn.mytask.Adapter.CompletedTaskAdapter;
import com.mayn.mytask.Adapter.TaskAdapter;
import com.mayn.mytask.Entity.CompletedTask;
import com.mayn.mytask.R;
import com.mayn.mytask.ViewModel.TaskViewModel;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class CompletedTaskFragment extends Fragment {

    public static CompletedTaskFragment newInstance(){

        return new CompletedTaskFragment();
    }

    public CompletedTaskFragment(){
        // Required empty public constructor
    }



    RecyclerView completedRecyclerView;

    //1.Create an Object of View ModelClass
    private TaskViewModel taskViewModel;

    //Create an Object of CompletedTaskAdapter Class
    CompletedTaskAdapter completedTaskAdapter = new CompletedTaskAdapter();
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.completed_task_fragment, container, false);

        completedRecyclerView = view.findViewById(R.id.recylerViewCompletedTask);

        //Setting layout manager to recycler view
        linearLayoutManager = new LinearLayoutManager(getActivity());

        //To reverse the Recycler View list
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        completedRecyclerView.setLayoutManager(linearLayoutManager);
        completedRecyclerView.setAdapter(completedTaskAdapter);


        //Assigning view model inside this OncreateView
        taskViewModel = (TaskViewModel) new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
                .create(TaskViewModel.class);

         taskViewModel.getAllCompletedTask().observe((LifecycleOwner) requireActivity(), new Observer<List<CompletedTask>>() {
             @Override
             public void onChanged(List<CompletedTask> completedTaskList) {
                 completedTaskAdapter.setCompletedTaskList(completedTaskList);
             }
         });

//         Swipe to Delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                taskViewModel.DeleteCompletedTask(completedTaskAdapter.getCompletedTask(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Task Deleted Permanently", Toast.LENGTH_SHORT).show();

            }

            //--->Decorative Swipe---->

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.dark_red))
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }



        }).attachToRecyclerView(completedRecyclerView);






        return view;
    }
}
