package org.launchcode.beerquiz.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class Question {
	
	private int answer;
	private List<QuizItem> items;
	
	public Question(int difficulty) throws JsonIOException, JsonSyntaxException, IOException {
		
		answer = (int) (Math.random() * difficulty);
	
		items = new ArrayList<QuizItem>();	
		for (int i = 0; i < difficulty; i++) {
			items.add(new QuizItem());
		}
	}

	public int getAnswer() {
		return answer;
	}

	public List<QuizItem> getItems() {
		return items;
	}


	
	

}
