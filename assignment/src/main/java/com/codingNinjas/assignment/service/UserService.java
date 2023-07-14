package com.codingNinjas.assignment.service;

import com.codingNinjas.assignment.exceptions.UserAlreadyExistsException;
import com.codingNinjas.assignment.model.Movie;
import com.codingNinjas.assignment.model.User;
import com.codingNinjas.assignment.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User addUser(User user) throws UserAlreadyExistsException {
        if (userRepo.findById(user.getUserId()).isEmpty())
        return userRepo.insert(user);
        else
            throw new UserAlreadyExistsException();
    }

    public void deleteUser(Integer id){
        userRepo.deleteById(id);
    }

    public User addMovie(String emailId, Movie movie){
        List<Movie> movies=new ArrayList<>();
        movies.add(movie);
        User user=userRepo.findByEmailId(emailId);
        if (user.getMovies()==null){
            user.setMovies(movies);
        }else
        {
        user.getMovies().add(movie);
        }
        userRepo.save(user);
        return user;
    }

    public User removeMovieFromUser(String emailId,Movie movie ){
        User user = userRepo.findByEmailId(emailId);
     List<Movie> moviesList = user.getMovies();
     if (moviesList!=null)
        moviesList.remove(movie);
     userRepo.save(user);
        return user;
    }
}
