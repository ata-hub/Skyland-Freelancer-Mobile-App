package com.example.freelancerzamantakibi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freelancerzamantakibi.retrofit.FreelancerApi;
import com.example.freelancerzamantakibi.retrofit.RetrofitService;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {
    EditText mail;
    EditText password;
    Button login;
    TextView errorText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        mail = findViewById(R.id.loginEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        errorText = findViewById(R.id.errorLoginText);
        login = findViewById(R.id.loginBTN);
        RetrofitService retrofitService = new RetrofitService();
        FreelancerApi freelancerApi = retrofitService.getRetrofit().create(FreelancerApi.class);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkNecessaryFields()){
                    //DatabaseHelper db = new DatabaseHelper(LoginPage.this);
                    String mailContent=mail.getText().toString();
                    String passwordContent=password.getText().toString();
                    //List<Freelancer> freelancers = db.getEveryOne();
                    Call<List<Freelancer>> allFreelancers = freelancerApi.getAllFreelancers();
                    allFreelancers.enqueue(new Callback<List<Freelancer>>() {
                        @Override
                        public void onResponse(Call<List<Freelancer>> call, Response<List<Freelancer>> response) {
                            List<Freelancer> freelancerList = response.body();
                            Freelancer user=null;
                            boolean userExists=false;
                            for(Freelancer f:freelancerList){
                                if(f.getEmail().equals(mailContent)&&f.getPassword().equals(passwordContent)){
                                    userExists=true;
                                    user=f;
                                }
                            }
                            if(userExists && user.isValidated()){
                                //Move to that persons account
                                Intent intent = new Intent(LoginPage.this, FreelancerPage.class);
                                intent.putExtra("user", user);
                                startActivity(intent);
                            }
                            else if(userExists && user.isValidated()==false){
                                errorText.setVisibility(View.VISIBLE);
                                errorText.setText("Your account is not validated by admin. Please wait for validation");
                            }
                            else{
                                errorText.setVisibility(View.VISIBLE);
                                errorText.setText("Please enter a correct email and password. Note that both fields can be case-sensitive.");

                            }
                        }

                        @Override
                        public void onFailure(Call<List<Freelancer>> call, Throwable t) {

                        }
                    });

                }
                else{

                }

            }
        });
    }
    public boolean checkNecessaryFields(){
        if(mail.getText().toString().trim().equals("")){
            mail.setError("Email is required");
            return false;
        }
        if(password.getText().toString().trim().equals("")){
            password.setError("Password is required");
            return false;
        }
        return true;
    }
}