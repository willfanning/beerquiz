package org.launchcode.beerquiz.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.beerquiz.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface UserDao extends CrudRepository<User, Integer> {

    User findByUid(int uid);
    
    User findByUsername(String username);
    
    List<User> findAll();
    
    // TODO findAllOrderByMaxScoreLimit10
    
}
