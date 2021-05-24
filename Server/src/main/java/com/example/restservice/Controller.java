package com.example.restservice;

import java.util.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	private List<RythmGroup> grouplist = new ArrayList<>();

	public void print() {
		for (RythmGroup track : grouplist) {
			System.out.printf("Track: %s, Rythm: %s, users:\n", track.getRythmName(), track.getRythm());
			for (User u : track.getUsers()) {
				System.out.println(u.getName() + " " + u.getScore());
			}
		}
		System.out.println("-------------------------------------------------------------------");
	}

	/*
	 * The following function takes an http request and put the name and score of
	 * the person in the dedicated group
	 */
	@GetMapping("/user")
	public User user(@RequestParam(value = "group", defaultValue = "") String group,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "score", defaultValue = "0") String score) {
		User user = new User(name, Integer.parseInt(score));
		// çheck if group is in the list with groups.
		for (RythmGroup track : grouplist) {
			if (group.equals(track.getRythmName())) {
				boolean exists = false;
				for (User u : track.getUsers()) {// check if their is not a duplicate user.
					if (name.equals(u.getName())) {
						exists = true;
						break;
					}
				}
				if (!exists) {
					track.addUser(user);// if there is not a duplicate add the user.
				} else {
					for (User u : track.getUsers()) { // else replace the score if higher.
						if (name.equals(u.getName())) {
							if (u.getScore() < Integer.parseInt(score)){
								u.setScore(Integer.parseInt(score));
							}
							
						}
					}
					break;
				}
			}
		}
		print();
		return user;
	}

	/*The following function takes a http request and makes a group out of that. */
	@GetMapping("/group")
	public RythmGroup rythmGroup(@RequestParam(value = "rythmname", defaultValue = "") String rythmname,
			@RequestParam(value = "rythm", defaultValue = "") String rythm) {

		RythmGroup mygroup = new RythmGroup(rythmname, rythm);
		boolean exists = false;
		//check if the group already exists
		for (RythmGroup track : grouplist) {
			if (rythmname.equals(track.getRythmName())) {
				exists = true;
				break;
			}
		}
		if (!exists) { //if it not exists: add to the list.
			grouplist.add(mygroup);
		}
		print();
		return mygroup;
	}
	/* The following function takes a http request with a group name in it, and returns the scoreboard of that group in the body.*/
	@GetMapping("/scoreboard")
	public Scoreboard scoreboard(@RequestParam(value = "rythmname") String rythmname) {
		Scoreboard name = new Scoreboard();
		//check if the group exists
		for (RythmGroup track : grouplist) {
			System.err.println(track.getRythmName() + rythmname);
			if (track.getRythmName().equals(rythmname)) {
				name = new Scoreboard(track); //make a scoreboard for that group.
				break;
			}
			
		}

		return name;

	}

}
