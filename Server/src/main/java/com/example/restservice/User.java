package com.example.restservice;

public class User {

	private final long id;
	private final int xscore;
	private final String name;

	public User(long id, String name, int score) {
		this.id = id;
		this.name = name;
		this.xscore = score;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getScore(){
		return xscore;
	}
}
