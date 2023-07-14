package com.codingNinjas.assignment;

import com.codingNinjas.assignment.controller.UserController;
import com.codingNinjas.assignment.exceptions.UserAlreadyExistsException;
import com.codingNinjas.assignment.model.Movie;
import com.codingNinjas.assignment.model.User;
import com.codingNinjas.assignment.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.MultipartConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;
    private User user;
    private Movie movie;

    @BeforeEach
    public void setup(){
        List<Movie> movies=new ArrayList<>();
        movie=new Movie("Avengers: Infinty War", "Joe Russo", 8.4);
        movies.add(movie);
        user=new User(10, "Sample Name", "sampleid@gmail.com", movies);
    mockMvc= MockMvcBuilders.standaloneSetup(userController).build();
    }

    @AfterEach
    public void clear(){
    movie=null;
    user=null;
    }

    @Test
    public void controllerTestForAddUserSucces() throws Exception {

        when(userService.addUser(user)).thenReturn(user);
        mockMvc.perform(
                post("/user/add-user").
                        contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjToJson(user))
            ).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(userService, times(1)).addUser(user);
    }

    @Test
    public void controllerTestForAddUserFailure() throws Exception{
        when(userService.addUser(user)).thenThrow(UserAlreadyExistsException.class);
        mockMvc.perform(
                post("/user/add-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjToJson(user))
        ).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
    verify(userService,times(1)).addUser(user);
    }

    @Test
    public void controllerTestForAddMovieSuccess() throws Exception{

        when(userService.addMovie(user.getEmailId(), movie)).thenReturn(user);
        mockMvc.perform(
                post("/user/add-movie/sampleid@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjToJson(movie))
        ).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    public static String convertObjToJson(final Object object){
        String value="";
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            value =objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return value;
    }
}

