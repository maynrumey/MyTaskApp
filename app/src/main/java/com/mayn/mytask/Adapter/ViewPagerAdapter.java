package com.mayn.mytask.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.mayn.mytask.Fragment.CompletedTaskFragment;
import com.mayn.mytask.Fragment.TaskFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        //Switch Between Fragment

        if (position == 1){

            return new CompletedTaskFragment();
        }

        return new TaskFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
