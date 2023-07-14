package com.codingNinjas.assignment;

import com.codingNinjas.assignment.model.Movie;
import com.codingNinjas.assignment.model.User;
import com.codingNinjas.assignment.repository.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    private User user;
    private Movie movie;

    @BeforeEach
    public void setUp() {
        movie = new Movie("Interstellar", "Christopher Nolan", 8.6);
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        user = new User(100, "Saurabh Pawar", "pawarsaurabh7399@gmail.com", movies);

    }


    @AfterEach
    public void clean() {
        movie = null;
        user = null;
        userRepo.deleteById(100);
    }

    @Test
    public void userInsertionTestSuccess() {
        userRepo.insert(user);
        User user1 = userRepo.findById(user.getUserId()).get();
        assertEquals(true, user1.equals(user));

    }

    @Test
    public void userInsertionFailure() {
            userRepo.insert(user);
            assertThrows(DuplicateKeyException.class, ()->userRepo.insert(user));
    }


}