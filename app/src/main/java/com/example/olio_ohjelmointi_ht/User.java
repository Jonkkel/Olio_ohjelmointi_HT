package com.example.olio_ohjelmointi_ht;


import java.io.Serializable;

public class User implements Serializable {
    String name;
    int age;
    String password;
    String username;
    String email;
    String city;

    public User(){

    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String inputPassword) {
        this.password = LogInTool.encrypt(inputPassword+name);
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
