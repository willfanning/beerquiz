package org.launchcode.beerquiz.controllers;

import java.io.IOException;
import java.util.ArrayList;
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
	
	@RequestMapping(value = "/leaderboard", method=RequestMethod.GET)
	public String leaderboard(Model model) {
		List<List<Quiz>> leaderboard = new ArrayList<>();
		
		for(int i = 2; i <=4; i++) {
			leaderboard.add(quizDao.findTop10ByDifficultyOrderByScoreDesc(i));
		}
		model.addAttribute("leaderboard", leaderboard);
	
		return "leaderboard";
	}

	@RequestMapping(value = "/quiz/newquiz", method = RequestMethod.GET)
	public String newQuizForm(HttpServletRequest request) {

		HttpSession session = request.getSession();

		// if quiz already in progress, remove from session and present new quiz form
		if (session.getAttribute("quiz") != null) {
			session.removeAttribute("quiz");
			session.removeAttribute("question");
		}
		return "newquiz";
	}

	@RequestMapping(value = "/quiz/newquiz", method = RequestMethod.POST)
	public String newQuiz(HttpServletRequest request) {

		HttpSession session = request.getSession();

		// get request parameter
		int difficulty = Integer.valueOf(request.getParameter("difficulty"));

		// initialize new quiz and add to session
		Quiz quiz = new Quiz(difficulty, getUserFromSession(request.getSession()).getUsername());
		session.setAttribute("quiz", quiz);

		return "redirect:/quiz";
	}

	@RequestMapping(value = "/quiz", method = RequestMethod.GET)
	public String quizForm(HttpServletRequest request, Model model) {

		// get current session and session attributes
		HttpSession session = request.getSession();
		Quiz quiz = (Quiz) session.getAttribute("quiz");
		Question question = (Question) session.getAttribute("question");

		// if quiz not in session, redirect to new quiz
		if (quiz == null) {
			return "redirect:quiz/newquiz";
		}
		// quiz in session --> proceed to question
		model.addAttribute("quiz", quiz);

		// if question already in progress, display that question
		// no skipping questions!
		if (question != null) {;} 
		else {
			// generate new question
			try {
				question = quiz.newQuestion();
				session.setAttribute("question", question);

			} catch (JsonIOException | JsonSyntaxException | IOException e) {
				e.printStackTrace();
				model.addAttribute("error", "There was an error generating the quiz question. Please try the quiz again");
				return "redirect:/";
			}
		}
		// List<QuizItem> items, QuizItem answer
		model.addAttribute("items", question.getItems());
		model.addAttribute("answer", question.getItems().get(question.getAnswer()));
		System.out.println(question.getAnswer());

		return "quiz_form";
	}

	@RequestMapping(value = "/quiz", method = RequestMethod.POST)
	public String quiz(HttpServletRequest request, Model model) {

		// get session and attributes
		HttpSession session = request.getSession();
		Quiz quiz = (Quiz) session.getAttribute("quiz");
		Question question = (Question) session.getAttribute("question");
		
		// if quiz not in session, redirect to new quiz
		/*
		 * if (quiz == null) {
			return "redirect:quiz/newquiz";
			}
		 */
		
		model.addAttribute("quiz", quiz);
		
		if (question == null) {
			return "redirect:/quiz";
		}
		
		// get answer from form
		if (request.getParameter("answer") == null) {
			model.addAttribute("error", "Make a selection");
			return "redirect:/quiz";
		}
		int ans = Integer.valueOf(request.getParameter("answer"));

		// correct answer
		if (ans == question.getAnswer()) {

			model.addAttribute("correct", true);
			quiz.setScore(quiz.getScore() + 1);

			QuizItem answer = question.getItems().get(ans);
			model.addAttribute("answer", answer);

		} else {
			session.removeAttribute("quiz");
			
			if (quiz.getScore() > 0) {
				quizDao.save(quiz);
			}
		}
		session.removeAttribute("question");
		return "quiz_result";
	}

}
