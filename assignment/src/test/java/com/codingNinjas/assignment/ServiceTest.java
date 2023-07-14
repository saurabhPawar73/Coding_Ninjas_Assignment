package com.codingNinjas.assignment;

import com.codingNinjas.assignment.exceptions.UserAlreadyExistsException;
import com.codingNinjas.assignment.model.Movie;
import com.codingNinjas.assignment.model.User;
import com.codingNinjas.assignment.repository.UserRepo;
import com.codingNinjas.assignment.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    //For Service and Controller, I am using Mockito along with JUnit
    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    private User user;
    private Movie movie;

    @BeforeEach
    public void setUp(){
        List<Movie> movies=new ArrayList<>();
        Movie movie1=new Movie("Harry Potter and The Deathly Hallows", "David Yates", 7.6);
        movies.add(movie1);
        user=new User(50, "Saurabh Pawar", "spawar@gmail.com", movies);
    }

    @AfterEach
    public void clean(){
        movie=null;
        user=null;
    }


        @Test
        public void addUserSuccesTest() throws UserAlreadyExistsException {

        when(userRepo.findById(user.getUserId())).thenReturn(Optional.ofNullable(null));
        when(userRepo.insert(user)).thenReturn(user);
        assertEquals(user, userService.addUser(user));
        }

        @Test
    public void addUserFailureTest(){
        when(userRepo.findById(user.getUserId())).thenReturn(Optional.of(user));
        assertThrows(UserAlreadyExistsException.class, ()->userService.addUser(user));
        }

}
