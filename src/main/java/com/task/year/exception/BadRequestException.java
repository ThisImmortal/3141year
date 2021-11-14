package com.task.year.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ControllerException{

    public BadRequestException(String message){

        super(HttpStatus.BAD_REQUEST, message);
    }
}
