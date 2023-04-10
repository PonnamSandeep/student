package com.student.student.Exception;

import java.io.Serial;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(String message) {
        super(message);
    }
}
