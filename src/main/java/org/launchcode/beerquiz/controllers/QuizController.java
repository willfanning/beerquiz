package org.launchcode.beerquiz.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuizController {

	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}
}
