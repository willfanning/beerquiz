package org.launchcode.beerquiz.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;



public class Quiz {
	
	private final int difficulty;
	private int score;
	private final Date quizTime;
	private final  List<Question> questions;
	
	public Quiz(int diff) {
		difficulty = diff;
		score = 0;
		quizTime = new Date();
		questions = new ArrayList<Question>();
	}
	
	public Question newQuestion() throws JsonIOException, JsonSyntaxException, IOException {
		Question q = new Question(difficulty);
		questions.add(q);
		return q;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Date getQuizTime() {
		return quizTime;
	}

	public List<Question> getQuestions() {
		return questions;
	}	
}
