package com.example.freelancerzamantakibi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Freelancer implements Serializable {
    private Long id;
    private String name;
    private String surname;
    private String role;
    private String password;
    private String phone;
    private String email;
    private String personalInfo;
    private boolean isValidated;
    private List<Task> tasks = new ArrayList<>();
    public Freelancer(String name, String surname, String role, String password, String phone,
                      String email, String personalInfo){
        this.name=name;
        this.surname=surname;
        this.role=role;
        this.password=password;
        this.phone=phone;
        this.email=email;
        this.personalInfo=personalInfo; //bir şey girilmezse null mu yoksa empty string mi?
        isValidated=false;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(String personalInfo) {
        this.personalInfo = personalInfo;
    }
    public void addTask(Task task){
        tasks.add(task);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }

    @Override
    public String toString() {
        return "Freelancer{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", personalInfo='" + personalInfo + '\'' +
                '}';
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
