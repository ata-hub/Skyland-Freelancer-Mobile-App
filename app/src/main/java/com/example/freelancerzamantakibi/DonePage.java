package com.example.freelancerzamantakibi;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class DonePage extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Freelancer user;
    ArrayList<Task> tasksToShow = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_page);
        drawerLayout=findViewById(R.id.drawerLayout);
        Intent intent = getIntent();
        user= (Freelancer) intent.getSerializableExtra("user");
        RecyclerView recyclerView = findViewById(R.id.doneRecyclerView);
        setUpTasks();
        TaskRecyclerViewAdapter adapter = new TaskRecyclerViewAdapter(this, tasksToShow);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void redirectActivity(Activity activity, Class aclass) {
        Intent intent = new Intent(activity, aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("user", user);
        activity.startActivity(intent);
    }
    public void ClickMenu(View view){
        FreelancerPage.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view){
        FreelancerPage.closeDrawer(drawerLayout);
    }
    public void ClickHome(View view){

        redirectActivity(this,FreelancerPage.class);
    }
    public void ToDo(View view){

        redirectActivity(this,ToDoPage.class);
    }
    public void InProgress(View view){

        redirectActivity(this,InProgressPage.class);
    }
    public void ClickAddTask(View view){

        redirectActivity(this,AddTaskPage.class);
    }
    public void Done(View view){
        recreate();
    }
    public void Logout(View view){
        FreelancerPage.logout(this);
    }
    @Override
    public void onPause(){
        super.onPause();
        FreelancerPage.closeDrawer(drawerLayout);
    }
    public void setUpTasks(){
        //get tasks from the user
        if(user.getTasks().size()==0){
            return;
        }
        for(Task t:user.getTasks()){
            if(t.getType().equals("done")){
                tasksToShow.add(t);
            }
        }
    }
}