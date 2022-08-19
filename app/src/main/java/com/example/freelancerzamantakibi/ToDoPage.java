package com.example.freelancerzamantakibi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freelancerzamantakibi.retrofit.FreelancerApi;
import com.example.freelancerzamantakibi.retrofit.RetrofitService;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToDoPage extends AppCompatActivity  {
    DrawerLayout drawerLayout;
    Freelancer user;
    TextView todoText;
    ArrayList<Task> tasksToShow = new ArrayList<>();
    TaskRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_page);
        todoText = findViewById(R.id.todoText);
        Intent intent = getIntent();
        user= (Freelancer) intent.getSerializableExtra("user");
        drawerLayout=findViewById(R.id.drawerLayout);
        RecyclerView recyclerView = findViewById(R.id.toDoRecyclerView);
        setUpTasks(user.getId());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TaskRecyclerViewAdapter adapter = new TaskRecyclerViewAdapter(this, tasksToShow);
        recyclerView.setAdapter(adapter);





    }
    /*
    @Override
    protected void onResume()
    {
        super.onResume();
        try {
            updateTasks(user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     */

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
        recreate();
    }
    public void InProgress(View view){

        redirectActivity(this,InProgressPage.class);
    }
    public void ClickAddTask(View view){

        redirectActivity(this,AddTaskPage.class);
    }
    public void Done(View view){

        redirectActivity(this,DonePage.class);
    }
    public void Logout(View view){
        FreelancerPage.logout(this);
    }
    @Override
    public void onPause(){
        super.onPause();
        FreelancerPage.closeDrawer(drawerLayout);
    }
    public void setUpTasks(Long id){

        if(user.getTasks().size()==0){
            return;
        }
        for(Task t:user.getTasks()){
            if(t.getType().equals("todo")){
                tasksToShow.add(t);
            }
        }

    }
    public void updateTasks(Long id){
        tasksToShow.clear();
        RetrofitService retrofitService = new RetrofitService();
        FreelancerApi freelancerApi = retrofitService.getRetrofit().create(FreelancerApi.class);
        Call<List<Task>> freelancerTasks = freelancerApi.getFreelancerTasks(id);
        freelancerTasks.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                user.setTasks(response.body());
                tasksToShow= (ArrayList<Task>) user.getTasks();
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {

            }
        });
        adapter.notifyDataSetChanged();
    }

}