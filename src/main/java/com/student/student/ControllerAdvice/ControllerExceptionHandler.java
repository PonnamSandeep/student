package com.student.student.ControllerAdvice;

import com.student.student.Exception.InvalidDetailsException;
import com.student.student.Exception.InvalidStudentDetailsException;
import com.student.student.Exception.StudentNotFoundException;
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
        return new ResponseEntity<>(invalidDetailsException.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
