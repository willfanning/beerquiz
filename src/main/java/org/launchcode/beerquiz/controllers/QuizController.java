package org.launchcode.beerquiz.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuizController extends AbstractController {

	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}
}

/*
 * Quiz Start Page:
 * 	select difficulty
 * 	start quiz --> quizContinue = true
 *  score = 0
 *  
 * Quiz Question Page:
 *	display abv and ibu of correct answer
 *	list options A/B/C/D
 *	select item
 *
 * Process Answer
 * 	if correct --> score += 1
 *  if wrong -- > quizContinue = false
 *  
 * Display Result
 *  if correct --> next question
 *  if wrong --> try again/ restart quiz
 */