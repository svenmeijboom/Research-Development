package com.example.restservice;

public class Scoreboard {
    private RythmGroup group;

    public Scoreboard(){

    }

    public Scoreboard(RythmGroup rythmname){
        this.group = rythmname;
    }

    public RythmGroup getGroup(){
        return group;
    }

}
