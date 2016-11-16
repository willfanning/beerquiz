package org.launchcode.beerquiz.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {

	private String username;
	private String pwHash;
	private int maxScore;
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public User(String username, String password) {
		super();
		
		if (!isValidUsername(username)) {
			throw new IllegalArgumentException("Invalid username");
		}
		
		this.username = username;
		this.pwHash = hashPassword(password);
		this.maxScore = 0;
	}
	
	// Spring empty constructor
	public User() {}
	
	@NotNull
	@Column(name = "username", unique = true)
	public String getUsername() {
		return username;
	}

	@SuppressWarnings("unused")
	private void setUsername(String username) {
		this.username = username;
	}
	
	@NotNull
	@Column(name = "pwhash")
	public String getPwHash() {
		return pwHash;
	}

	@SuppressWarnings("unused")
	private void setPwHash(String pwHash) {
		this.pwHash = pwHash;
	}
	
	@NotNull
	@Column(name = "maxscore")
	public int getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}

	private static String hashPassword(String password) {		
		return encoder.encode(password);
	}
	
	public boolean isMatchingPassword(String password) {
		return encoder.matches(password, pwHash);
	}
	
	public static boolean isValidPassword(String password) {
		Pattern validPasswordPattern = Pattern.compile("(\\S){6,20}");
		Matcher matcher = validPasswordPattern.matcher(password);
		return matcher.matches();
	}
	
	public static boolean isValidUsername(String username) {
		Pattern validUsernamePattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9_-]{3,20}");
		Matcher matcher = validUsernamePattern.matcher(username);
		return matcher.matches();
	}
}
