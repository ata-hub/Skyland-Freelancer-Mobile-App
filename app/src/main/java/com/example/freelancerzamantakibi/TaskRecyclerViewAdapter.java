package com.example.freelancerzamantakibi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freelancerzamantakibi.retrofit.FreelancerApi;
import com.example.freelancerzamantakibi.retrofit.RetrofitService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.MyViewHolder> {
    Context context;
    List<Task> tasks;

    public TaskRecyclerViewAdapter(Context context, ArrayList<Task> tasks) {
        this.context=context;
        this.tasks=tasks;
    }

    @NonNull
    @Override
    public TaskRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //this is where you inflate the layout (Giving a look to the rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_task_style, parent, false);
        return new TaskRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskRecyclerViewAdapter.MyViewHolder holder, int position) {
        //assigning values to the views that is created in the recycler_view_task_style
        //based on the position of the recycler view
        RetrofitService retrofitService = new RetrofitService();
        FreelancerApi freelancerApi = retrofitService.getRetrofit().create(FreelancerApi.class);
        holder.title.setText(tasks.get(position).getTitle());
        holder.timeLeft.setText("Time left: "+tasks.get(position).getCountTime()+" days");
        //buttons
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Task info")
                        .setMessage(tasks.get(holder.getAdapterPosition()).getInfo())
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                builder.setCancelable(true);
                            }
                        }).show();


            }
        });
        holder.move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context instanceof InProgressPage){
                    Task t = tasks.get(holder.getAdapterPosition());
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Move Task")
                            .setMessage(tasks.get(holder.getAdapterPosition()).getInfo())
                            .setPositiveButton("Move to to do", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    t.setType("todo");
                                    Call<Task> taskCall = freelancerApi.updateTask(t);
                                    taskCall.enqueue(new Callback<Task>() {
                                        @Override
                                        public void onResponse(Call<Task> call, Response<Task> response) {
                                            Toast.makeText(context, "Task updated successfully", Toast.LENGTH_SHORT).show();
                                            tasks.remove(holder.getAdapterPosition());
                                            notifyItemRemoved(holder.getAdapterPosition());
                                        }

                                        @Override
                                        public void onFailure(Call<Task> call, Throwable t) {
                                            Toast.makeText(context, "Task update failed", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                    builder.setCancelable(true);
                                }
                            })
                            .setNegativeButton("Move to done", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    t.setType("done");
                                    Call<Task> taskCall = freelancerApi.updateTask(t);
                                    taskCall.enqueue(new Callback<Task>() {
                                        @Override
                                        public void onResponse(Call<Task> call, Response<Task> response) {
                                            Toast.makeText(context, "Task updated successfully", Toast.LENGTH_SHORT).show();
                                            tasks.remove(holder.getAdapterPosition());
                                            notifyItemRemoved(holder.getAdapterPosition());
                                        }

                                        @Override
                                        public void onFailure(Call<Task> call, Throwable t) {
                                            Toast.makeText(context, "Task update failed", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                    builder.setCancelable(true);
                                }
                            })
                            .show();
                    //move to done page

                }
                else if(context instanceof ToDoPage){
                    //move to in progress
                    Task t = tasks.get(holder.getAdapterPosition());
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Move Task")
                            .setMessage(tasks.get(holder.getAdapterPosition()).getInfo())
                            .setPositiveButton("Move to in progress", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    t.setType("inprogress");
                                    Call<Task> taskCall = freelancerApi.updateTask(t);
                                    taskCall.enqueue(new Callback<Task>() {
                                        @Override
                                        public void onResponse(Call<Task> call, Response<Task> response) {
                                            Toast.makeText(context, "Task updated successfully", Toast.LENGTH_SHORT).show();
                                            tasks.remove(holder.getAdapterPosition());
                                            notifyItemRemoved(holder.getAdapterPosition());
                                        }

                                        @Override
                                        public void onFailure(Call<Task> call, Throwable t) {
                                            Toast.makeText(context, "Task update failed", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                    builder.setCancelable(true);
                                }
                            })
                            .setNegativeButton("Move to done", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    t.setType("done");
                                    Call<Task> taskCall = freelancerApi.updateTask(t);
                                    taskCall.enqueue(new Callback<Task>() {
                                        @Override
                                        public void onResponse(Call<Task> call, Response<Task> response) {
                                            Toast.makeText(context, "Task updated successfully", Toast.LENGTH_SHORT).show();
                                            tasks.remove(holder.getAdapterPosition());
                                            notifyItemRemoved(holder.getAdapterPosition());
                                        }

                                        @Override
                                        public void onFailure(Call<Task> call, Throwable t) {
                                            Toast.makeText(context, "Task update failed", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                    builder.setCancelable(true);
                                }
                            })
                            .show();
                    //move to done page


                }
                else if(context instanceof DonePage){
                    //dont move or move back to in progress
                    Task t = tasks.get(holder.getAdapterPosition());
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Move Task")
                            .setMessage(tasks.get(holder.getAdapterPosition()).getInfo())
                            .setPositiveButton("Move to to do", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    t.setType("todo");
                                    Call<Task> taskCall = freelancerApi.updateTask(t);
                                    taskCall.enqueue(new Callback<Task>() {
                                        @Override
                                        public void onResponse(Call<Task> call, Response<Task> response) {
                                            Toast.makeText(context, "Task updated successfully", Toast.LENGTH_SHORT).show();
                                            tasks.remove(holder.getAdapterPosition());
                                            notifyItemRemoved(holder.getAdapterPosition());
                                        }

                                        @Override
                                        public void onFailure(Call<Task> call, Throwable t) {
                                            Toast.makeText(context, "Task update failed", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                    builder.setCancelable(true);
                                }
                            })
                            .setNegativeButton("Move to in progress", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    t.setType("inprogress");
                                    Call<Task> taskCall = freelancerApi.updateTask(t);
                                    taskCall.enqueue(new Callback<Task>() {
                                        @Override
                                        public void onResponse(Call<Task> call, Response<Task> response) {
                                            Toast.makeText(context, "Task updated successfully", Toast.LENGTH_SHORT).show();
                                            tasks.remove(holder.getAdapterPosition());
                                            notifyItemRemoved(holder.getAdapterPosition());
                                        }

                                        @Override
                                        public void onFailure(Call<Task> call, Throwable t) {
                                            Toast.makeText(context, "Task update failed", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                    builder.setCancelable(true);
                                }
                            })
                            .show();
                    //move to done page
                }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task taskToRemove=tasks.get(holder.getAdapterPosition());
                tasks.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                Call<Task> taskCall = freelancerApi.deleteTask(taskToRemove.getId());
                taskCall.enqueue(new Callback<Task>() {
                    @Override
                    public void onResponse(Call<Task> call, Response<Task> response) {
                        Toast.makeText(context, "Task with task id:"+ taskToRemove.getId() +"deleted successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Task> call, Throwable t) {
                        Toast.makeText(context, "Task deletion failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
    public void upDateData(List<Task> updateTasks) {

        tasks.addAll(updateTasks);
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        //kind of like onCreate method
        TextView title,timeLeft;
        FloatingActionButton info,move,delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.taskTitle);
            timeLeft=itemView.findViewById(R.id.taskTime);
            info=itemView.findViewById(R.id.taskInfoBtn);
            move=itemView.findViewById(R.id.goToInprogressBtn);
            delete=itemView.findViewById(R.id.deleteTaskBtn);
        }
    }
}
