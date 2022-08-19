package com.example.freelancerzamantakibi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.freelancerzamantakibi.retrofit.FreelancerApi;
import com.example.freelancerzamantakibi.retrofit.RetrofitService;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpPage extends AppCompatActivity {
    private EditText name;
    private EditText surname;
    private EditText role;
    private EditText phone;
    private EditText password;
    private EditText email;
    private EditText about;
    private Button signupBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        name=findViewById(R.id.signupName);
        surname=findViewById(R.id.signupSurName);
        role=findViewById(R.id.role);
        phone=findViewById(R.id.editTextPhone);
        password=findViewById(R.id.editTextTextPassword);
        email=findViewById(R.id.editTextTextEmailAddress);
        about=findViewById(R.id.infoBubbleText);
        signupBtn=findViewById(R.id.signupBtn);
        RetrofitService retrofitService = new RetrofitService();
        FreelancerApi freelancerApi = retrofitService.getRetrofit().create(FreelancerApi.class);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Buton tıklandığında önce boş yerleri kontrol et
                boolean isFilled=checkNecessaryFields();
                if(isFilled==true){
                    //Bütün girilenleri değişkenlerde sakla
                    String username = name.getText().toString();
                    String userSurname = surname.getText().toString();
                    String usermail = email.getText().toString();
                    String userPhone = phone.getText().toString();
                    String userRole = role.getText().toString();
                    String userPassword = password.getText().toString();
                    String userAbout = about.getText().toString();
                    DatabaseHelper dbHelper = new DatabaseHelper(SignUpPage.this);
                    //Freelancer objesini yarat, Database kısmına kaydet
                    Freelancer freelancer = new Freelancer(username,userSurname,userRole,userPassword,userPhone,usermail,userAbout);
                    //boolean success= dbHelper.addOne(freelancer);
                    freelancerApi.addFreelancer(freelancer).enqueue(new Callback<Freelancer>() {
                        @Override
                        public void onResponse(Call<Freelancer> call, Response<Freelancer> response) {
                            Toast.makeText(SignUpPage.this, "Save to api Successful", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Freelancer> call, Throwable t) {
                            Toast.makeText(SignUpPage.this, "Save to api Failed", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(SignUpPage.class.getName()).log(Level.SEVERE,"Error occured",t);
                        }
                    });
                    //Toast.makeText(SignUpPage.this, "Save to Sqlite Success= "+success, Toast.LENGTH_SHORT).show();

                    //bir sonraki sayfaya geç
                    Intent intent = new Intent(SignUpPage.this, WaitingPage.class);
                    startActivity(intent);
                }
            }
        });


    }
    public boolean checkNecessaryFields(){
        if(name.getText().toString().trim().equals("")){
            name.setError("Name is required");
            return false;
        }
        if(surname.getText().toString().trim().equals("")){
            surname.setError("Surname is required");
            return false;
        }
        if(role.getText().toString().trim().equals("")){
            role.setError("Role is required");
            return false;
        }
        if(phone.getText().toString().trim().equals("")){
            phone.setError("Phone is required");
            return false;
        }
        if(password.getText().toString().trim().equals("")){
            password.setError("Password is required");
            return false;
        }
        if(email.getText().toString().trim().equals("")){
            email.setError("Email is required");
            return false;
        }
        return true;
    }
}