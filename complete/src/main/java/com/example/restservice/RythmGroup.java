package com.example.restservice;

import java.util.*;

public class RythmGroup {
    
    private String rythmname;
    private String rythm;
    private List<User> userlist = new ArrayList<>();

    public RythmGroup(){
        
    }

    public RythmGroup (String rythmname, String rythm){
        this.rythmname = rythmname;
        this.rythm = rythm;
    }

    public String getRythmName(){
        return rythmname;
    }

    public String getRythm(){
        return rythm;
    }

    public void addUser(User user){
        userlist.add(user);
    }

    public List<User> getUsers(){
        return userlist;
    }
}