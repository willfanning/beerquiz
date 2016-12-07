package org.launchcode.beerquiz.controllers;

import javax.servlet.http.HttpSession;

import org.launchcode.beerquiz.models.User;
import org.launchcode.beerquiz.models.dao.QuizDao;
import org.launchcode.beerquiz.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractController {
	
	@Autowired
    protected UserDao userDao;
	
	@Autowired
	protected QuizDao quizDao;
	
	public static final String userSessionKey = "user_id";

    protected User getUserFromSession(HttpSession session) {
    	
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        return userId == null ? null : userDao.findByUid(userId);
    }
    
    protected void setUserInSession(HttpSession session, User user) {
    	session.setAttribute(userSessionKey, user.getUid());
    }
}
