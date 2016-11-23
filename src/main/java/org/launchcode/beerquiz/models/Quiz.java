package org.launchcode.beerquiz.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;


@Entity
@Table(name = "quiz")
public class Quiz extends AbstractEntity {
	
	private int difficulty;
	private int score;
	private Date quizTime;
	private String quizTaker;
	
	public Quiz() {}
	
	public Quiz(int diff, String username) {
		super();
		quizTaker = username;
		difficulty = diff;
		score = 0;
		quizTime = new Date();
	}
	
	public Question newQuestion() throws JsonIOException, JsonSyntaxException, IOException {
		return new Question(difficulty);
		
	}

	@NotNull
	@Column(name = "difficulty")
	public int getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	@NotNull
	@Column(name = "score")
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	@NotNull
	@Column(name = "quizTime")
	public Date getQuizTime() {
		return quizTime;
	}

	public void setQuizTime(Date quizTime) {
		this.quizTime = quizTime;
	}

	@NotNull
	@Column(name = "quizTaker")
	public String getQuizTaker() {
		return quizTaker;
	}

	public void setQuizTaker(String quizTaker) {
		this.quizTaker = quizTaker;
	}

}
