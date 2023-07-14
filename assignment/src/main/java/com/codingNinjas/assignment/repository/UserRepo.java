package com.codingNinjas.assignment.repository;

import com.codingNinjas.assignment.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<User,Integer> {

     User findByEmailId(String emailId);
}
