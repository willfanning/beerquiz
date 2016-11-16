package org.launchcode.beerquiz.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {

	private String username;
	private String pwHash;
	
	public User(String username, String password) {
		
	}
	
	public User() {}
}
