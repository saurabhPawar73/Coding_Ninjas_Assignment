package com.codingNinjas.assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User with given id already exists")
public class UserAlreadyExistsException extends Exception{
}
