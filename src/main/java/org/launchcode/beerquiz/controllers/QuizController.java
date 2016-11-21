package org.launchcode.beerquiz.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.beerquiz.models.Question;
import org.launchcode.beerquiz.models.Quiz;
import org.launchcode.beerquiz.models.QuizItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

@Controller
public class QuizController extends AbstractController {

	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/newquiz", method = RequestMethod.GET)
	public String newQuizForm(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("quiz") != null) {
			session.removeAttribute("quiz");
		}
		
		return "newquiz";
	}

	@RequestMapping(value = "/newquiz", method = RequestMethod.POST)
	public String newQuiz(HttpServletRequest request) {

		HttpSession session = request.getSession();
		
		// get request parameter
		int difficulty = Integer.valueOf(request.getParameter("difficulty"));

		// initialize new quiz and add to session
		Quiz quiz = new Quiz(difficulty);
		session.setAttribute("quiz", quiz);

		return "redirect:/quiz";

	}

	@RequestMapping(value = "/quiz", method = RequestMethod.GET)
	public String quizForm(HttpServletRequest request, Model model) {

		// get current session and session attributes
		HttpSession session = request.getSession();

		// if quiz not in session, redirect to newquiz
		if (session.getAttribute("quiz") == null) {
			return "redirect:/newquiz";
		}
		Quiz quiz = (Quiz) session.getAttribute("quiz");
		model.addAttribute("score", quiz.getScore());
		
		// if question already in progress, display that question
		if (session.getAttribute("question") != null) {
			Question question = (Question) session.getAttribute("question");
			List<QuizItem> items = question.getItems();
			model.addAttribute("items", items);
			model.addAttribute("answer_abv", items.get(question.getAnswer()).getAbv());
			model.addAttribute("answer_ibu", items.get(question.getAnswer()).getIbu());
			
			return "quiz_form";
		}
		
		

		Question question;

		try {
			question = quiz.newQuestion();
			session.setAttribute("question", question);
			
			List<QuizItem> items = question.getItems();
			model.addAttribute("items", items);
			model.addAttribute("answer_abv", items.get(question.getAnswer()).getAbv());
			model.addAttribute("answer_ibu", items.get(question.getAnswer()).getIbu());
			
		} catch (JsonIOException | JsonSyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "quiz_form";
	}
	
	@RequestMapping(value = "/quiz", method = RequestMethod.POST)
	public String quiz(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
		
		// get answer from form
		int ans = Integer.valueOf(request.getParameter("answer"));
		
		// get quiz question from session
		Quiz quiz = (Quiz) session.getAttribute("quiz");
		Question question = (Question) session.getAttribute("question");
		
//		List<Question> questions = quiz.getQuestions();
//		Question q = questions.get(questions.size() - 1);
		
		
		// correct answer: increment score, add image and link to brewery, next question button
		if (ans == question.getAnswer()) {
			
			session.removeAttribute("question");
			
			model.addAttribute("correct", true);
			
			quiz.setScore(quiz.getScore() + 1);
			
			QuizItem answer = question.getItems().get(ans);
			
			model.addAttribute("beer", answer);
			
		} else {
			session.removeAttribute("quiz");
			
			model.addAttribute("score", quiz.getScore());
			
			// save quiz to db for leaderboard
			
			
		}
		
		
		return "quiz_result";
	}

}

/*
 * Quiz Start Page: select difficulty start quiz --> quizContinue = true score =
 * 0
 * 
 * Quiz Question Page: display abv and ibu of correct answer list options
 * A/B/C/D select item
 *
 * Process Answer if correct --> score += 1 if wrong -- > quizContinue = false
 * 
 * Display Result if correct --> next question if wrong --> try again/ restart
 * quiz
 */