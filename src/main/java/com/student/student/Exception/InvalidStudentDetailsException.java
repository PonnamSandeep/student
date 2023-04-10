package com.student.student.Exception;

public class InvalidStudentDetailsException extends RuntimeException{
    public InvalidStudentDetailsException(String message) {
        super(message);
    }
}
