package com.example.restservice;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	RythmGroup mygroup = new RythmGroup("Track1", "dododododo");

	@GetMapping("/user")
	public User user(@RequestParam(value = "name", defaultValue = "geen naam") String name) {

		User user = new User(1,name, 15);
		mygroup.addUser(user);	
		return user;
	}
	@GetMapping("/group")
	public RythmGroup rythmGroup(@RequestParam(value = "rythmname", defaultValue = "") String rythmname){

		mygroup.addUser(new User(1,"Janssen", 2));
		mygroup.addUser(new User(0,"Pietersen", 15));
		mygroup.addUser(new User(2,"Harryiedt", 18));
		mygroup.addUser(new User(5,"Sambalsauys", 3555));
		return mygroup;
	}
	
}
