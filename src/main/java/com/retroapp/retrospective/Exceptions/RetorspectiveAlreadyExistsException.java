package com.retroapp.retrospective.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RetorspectiveAlreadyExistsException extends RuntimeException {

    public RetorspectiveAlreadyExistsException(String message) { super(message); }

}