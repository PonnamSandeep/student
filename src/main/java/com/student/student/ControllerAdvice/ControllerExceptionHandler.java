package com.student.student.ControllerAdvice;

import com.student.student.Exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<Object> handleStudentNotFoundException(StudentNotFoundException studentNotFoundException){
        return new ResponseEntity<>(studentNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidStudentDetailsException.class)
    public ResponseEntity<Object> handleInvalidStudentDetailsException(InvalidStudentDetailsException invalidStudentDetailsException){
        return new ResponseEntity<Object>(invalidStudentDetailsException.getMessage(),HttpStatus.NO_CONTENT);
    }
    @ExceptionHandler(InvalidDetailsException.class)
    public ResponseEntity<Object> handleInvalidDetailsException(InvalidDetailsException invalidDetailsException){
        return new ResponseEntity<>(invalidDetailsException.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Object> AlreadyExistsException(AlreadyExistsException alreadyExistsException){
        return new ResponseEntity<>(alreadyExistsException.getMessage(),HttpStatus.ALREADY_REPORTED);
    }
    @ExceptionHandler(FailedException.class)
    public ResponseEntity<Object> FailedException(FailedException failedException){
        return new ResponseEntity<>(failedException.getMessage(),HttpStatus.EXPECTATION_FAILED);
    }
    @ExceptionHandler(IncompleteException.class)
    public ResponseEntity<Object> IncompleteException(IncompleteException incompleteException){
        return new ResponseEntity<>(incompleteException.getMessage(),HttpStatus.PARTIAL_CONTENT);
    }
    @ExceptionHandler(DoesnotExitsException.class)
    public ResponseEntity<Object> DoesnotExitsException(DoesnotExitsException doesnotExitsException){
        return new ResponseEntity<>(doesnotExitsException.getMessage(),HttpStatus.NOT_FOUND);
    }
}
