package com.student.student.Exception;

public class InvalidDetailsException extends RuntimeException{
    public InvalidDetailsException(String message){
        super(message);
    }
}
