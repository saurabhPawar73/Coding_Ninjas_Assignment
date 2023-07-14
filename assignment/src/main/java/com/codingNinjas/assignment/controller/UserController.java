package com.codingNinjas.assignment.controller;

import com.codingNinjas.assignment.exceptions.UserAlreadyExistsException;
import com.codingNinjas.assignment.model.Movie;
import com.codingNinjas.assignment.model.User;
import com.codingNinjas.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add-user")
    public ResponseEntity<?> addUser(@RequestBody User user) throws UserAlreadyExistsException {
        try{
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
        }catch (UserAlreadyExistsException ex){
            throw new UserAlreadyExistsException();
        }
    }

    @DeleteMapping("/remove-user/{id}")
    public void removeUser(@PathVariable(name = "id")Integer id){
        userService.deleteUser(id);
    }

    @PostMapping("/add-movie/{emailId}")
    public ResponseEntity<?> addMovie(@RequestBody Movie movie, @PathVariable(name = "emailId")String emailId){
        return new ResponseEntity<>(userService.addMovie(emailId, movie), HttpStatus.OK);
    }

    @PostMapping("/remove-movie/{emailId}")
    public ResponseEntity<?> removeMovieFromUser(@RequestBody Movie movie, @PathVariable(name = "emailId")String eid){
        return new ResponseEntity<>(userService.removeMovieFromUser(eid, movie), HttpStatus.OK);
    }

}
