package org.launchcode.beerquiz.models;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class QuizQuestion {
	
	private String answer;
	private ArrayList<QuizItem> items;
	
	public QuizQuestion(int difficulty) throws JsonIOException, JsonSyntaxException, IOException {
		
		int randNum = ((int) (Math.random() * difficulty));
		
		if (randNum == 0) this.answer = "A"; 
		else if (randNum == 1) this.answer = "B"; 
		else if (randNum == 2) this.answer = "C"; 
		else this.answer = "D";
		
		for (int i = 0; i < difficulty; i++) {
			items.add(new QuizItem(i));
		}
	}

	public String getAnswer() {
		return answer;
	}

	public ArrayList<QuizItem> getItems() {
		return items;
	}


	
	

}
