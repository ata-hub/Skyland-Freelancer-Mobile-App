package com.example.freelancerzamantakibi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class FreelancerPage extends AppCompatActivity {
    private Freelancer user;
    private TextView welcome;
    DrawerLayout drawerLayout;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_page);
        drawerLayout=findViewById(R.id.drawerLayout);

        welcome=findViewById(R.id.welcome);
        Intent intent = getIntent();
        user = (Freelancer) intent.getSerializableExtra("user");
        welcome.setText("Hello "+user.getName()+" "+user.getSurname());

    }
    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }
    public void redirectActivity(Activity activity, Class aclass) {
        Intent intent = new Intent(activity, aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("user", user);
        activity.startActivity(intent);
    }
    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public void ClickHome(View view){
        recreate();
    }
    public void ToDo(View view){
        redirectActivity(this,ToDoPage.class);
    }
    public void InProgress(View view){
        redirectActivity(this,InProgressPage.class);
    }
    public void Done(View view){
        redirectActivity(this,DonePage.class);
    }
    public void Logout(View view){
        logout(this);
    }
    public void ClickAddTask(View view){
        redirectActivity(this,AddTaskPage.class);
    }

    public static void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
    @Override
    public void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }
}