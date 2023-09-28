package com.retroapp.retrospective.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RetorspectiveUnknownException extends RuntimeException {

    public RetorspectiveUnknownException(String message) { super(message); }

}