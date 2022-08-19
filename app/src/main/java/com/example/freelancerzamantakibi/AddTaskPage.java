package com.example.freelancerzamantakibi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.freelancerzamantakibi.retrofit.FreelancerApi;
import com.example.freelancerzamantakibi.retrofit.RetrofitService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTaskPage extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {
    DrawerLayout drawerLayout;
    FloatingActionButton addTaskBtn;
    Freelancer user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_page);
        drawerLayout = findViewById(R.id.drawerLayout);
        addTaskBtn = findViewById(R.id.addTaskButton);
        Intent intent = getIntent();
        user= (Freelancer) intent.getSerializableExtra("user");
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open a dialog, take input and create task object
                openDialog();

            }
        });
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
        recreate();
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
    public void openDialog(){
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(),"example dialog");
    }

    @Override
    public void applyText(String title, String info, String dueTime) {
        //create task
        Task task = new Task(title,dueTime,info);
        RetrofitService retrofitService = new RetrofitService();
        FreelancerApi freelancerApi = retrofitService.getRetrofit().create(FreelancerApi.class);
        freelancerApi.addTask(user.getId(),task).enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                Toast.makeText(AddTaskPage.this, "Task added successfully to id " + user.getId(), Toast.LENGTH_SHORT).show();
                Call<List<Task>> freelancerTasks = freelancerApi.getFreelancerTasks(user.getId());
                freelancerTasks.enqueue(new Callback<List<Task>>() {
                    @Override
                    public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                        user.setTasks(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Task>> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                Toast.makeText(AddTaskPage.this, "Couldn't add task to " + user.getName()+"id: "+ user.getId(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}