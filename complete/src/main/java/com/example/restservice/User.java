package com.example.restservice;

public class User {

	
	private int xscore;
	private final String name;

	public User(String name, int score) {
		this.name = name;
		this.xscore = score;
	}


	public String getName() {
		return name;
	}

	public int getScore(){
		return xscore;
	}

	public void setScore(int score){
		this.xscore = score;
	}
}
