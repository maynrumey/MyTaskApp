package com.mayn.mytask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mayn.mytask.Adapter.ViewPagerAdapter;
import com.mayn.mytask.Fragment.CompletedTaskFragment;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    //Initializing TabLayout
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.myCustomToolbar);
        setSupportActionBar(toolbar);
        //To hide toolbar Auto App title
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Defining TabLayout Id

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager_2);

        //---> Creating Object of View PagerAdapter and adding adapter to ViewPager
        ViewPagerAdapter viewPagerAdapter =new ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        viewPager2.setAdapter(viewPagerAdapter);
        viewPager2.setUserInputEnabled(false);//To make disable swipe on ViewPager


        //--->We will use "TabLayoutMediator" class to bind viewPager and Tablayout
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2,
                true, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //-->First parameter tab Object- represent the each tab
                //-->position, using position veritable, we will determine the text on our Tablayout.
                //-->We will use switch case for tab position
                //Finally tabLayoutMediator need attach as below
                switch (position){

                    case 0:
                        tab.setText("New Task");
                        break;
                    case 1:
                        tab.setText("Completed Task");
                        break;
                    default:
                        break;
                }
            }
        });
        tabLayoutMediator.attach();








    }










    //------>Overrides method for Menu<--------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.custom_menu,menu);

        return true;

    }
    //Methods for setting Listener for menu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.menuAddTask:

                //TODO: Intent add task Fragment

                break;
            case R.id.menuCompletedTask:

                Intent intent = new Intent(MainActivity.this, CompletedTaskFragment.class);
                startActivity(intent);
                //TODO: Show Completed Task
                break;
            case R.id.aboutMe:

                //TODO: Show about me on DialogBox
                break;
            default:

                break;

        }

        return true;
    }

}