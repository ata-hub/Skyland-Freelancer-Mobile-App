package com.example.freelancerzamantakibi.retrofit;

import com.example.freelancerzamantakibi.Freelancer;
import com.example.freelancerzamantakibi.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FreelancerApi {

    @GET("/api/v1/freelancer")
    Call<List<Freelancer>> getAllFreelancers();

    @GET("/api/v1/tasks")
    Call<List<Task>> getAllTasks();

    @GET("/api/v1/freelancers/{freelancerId}/tasks")
    Call<List<Task>> getFreelancerTasks(@Path("freelancerId") Long taskId);

    @POST("/api/v1/freelancer")
    Call<Freelancer> addFreelancer(@Body Freelancer freelancer);

    @POST("/api/v1/freelancers/{freelancerId}/tasks")
    Call<Task> addTask(@Path("freelancerId") Long id, @Body Task task);

    @PUT("/api/v1/freelancers/tasks")
    Call<Task> updateTask(@Body Task task);

    @DELETE("/api/v1/freelancers/tasks/{taskId}")
    Call<Task> deleteTask(@Path("taskId") Long taskId);

}
