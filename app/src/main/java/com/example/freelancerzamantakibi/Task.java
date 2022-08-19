package com.example.freelancerzamantakibi;


import java.io.Serializable;

public class Task implements Serializable {
    private Long id;
    private String title;
    private String info;
    private String type;
    private String dueTime;
    private String countTime;
    public Task(String title, String time, String info){
        this.title=title;
        this.info=info;
        this.type="todo";
        this.dueTime=time;
    }
    public Task(String title,String time){
        this.title=title;
        this.type="todo";
        this.info="";
        this.dueTime=time;

    }
    public void addExplanation(String exp){
        this.info=exp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public String getCountTime() {
        return countTime;
    }

    public void setCountTime(String countTime) {
        this.countTime = countTime;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
