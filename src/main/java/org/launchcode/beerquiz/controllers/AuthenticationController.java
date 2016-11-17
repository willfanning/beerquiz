package org.launchcode.beerquiz.controllers;

import javax.servlet.http.HttpServletRequest;

import org.launchcode.beerquiz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController extends AbstractController {

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registrationForm() {
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registration(HttpServletRequest request, Model model) {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String verify = request.getParameter("verify");

		User existingUser = userDao.findByUsername(username);

		if (existingUser != null) {

			model.addAttribute("username_error", "That username is already taken");
			return "register";
		}

		// username not already taken --> validate submitted fields
		
		if (!User.isValidUsername(username)) {
			
			model.addAttribute("username_error",
					"Invalid username. Must be 4-20 characters in length. Must begin with a letter "
							+ "and contain only letter, number, underscore( _ ) and dash( - ) characters");
			return "register";
		}
		
		// username valid to this point, add to model --> proceed to validate password and verify
		model.addAttribute("username", username);

		if (!User.isValidPassword(password)) {
			
			model.addAttribute("password_error",
					"Invalid password. Must be 6 - 20 characters in length " + "and contain no whitespace characters");
			return "register";
		}

		if (!password.equals(verify)) {
			
			model.addAttribute("verify_error", "Invalid password verification");
			return "register";
		}

		// all fields valid --> create user, add to table, add to session

		User user = new User(username, password);
		userDao.save(user);
		setUserInSession(request.getSession(), user);
		return "redirect:/";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		User user = userDao.findByUsername(username);

		if (user == null) {

			model.addAttribute("username_error", "Username not found");
			return "login";

		} else {
			if (!user.isMatchingPassword(password)) {

				model.addAttribute("username", username);
				model.addAttribute("password_error", "Invalid password");
				return "login";
			}
		}

		setUserInSession(request.getSession(), user);
		return "redirect:/";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/";
	}
}
