package com.task.year.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControllerException extends RuntimeException{

    private HttpStatus httpStatus;
    private String message;
}
