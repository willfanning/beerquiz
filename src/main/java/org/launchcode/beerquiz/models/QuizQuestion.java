package org.launchcode.beerquiz.models;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class QuizQuestion {
	
	private final int answer;
	ArrayList<QuizItem> items;
	
	public QuizQuestion(int difficulty) throws JsonIOException, JsonSyntaxException, IOException {
		answer = ((int) (Math.random() * difficulty));
		
		for (int i = 0; i < difficulty; i++) {
			items.add(new QuizItem());
		}
	}

	public int getAnswer() {
		return answer;
	}

}
