package com.codingNinjas.assignment.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private Integer userId;
    private String name, emailId;
    private List<Movie> movies;
}
