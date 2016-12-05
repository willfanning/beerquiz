package org.launchcode.beerquiz.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.beerquiz.models.Quiz;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface QuizDao extends CrudRepository<Quiz, Integer>{
	
	List<Quiz> findAll();
	
	List<Quiz> findByQuizTaker(String quizTaker);
	
	List<Quiz> findTop10ByDifficultyOrderByScoreDesc(int difficulty);	

}
