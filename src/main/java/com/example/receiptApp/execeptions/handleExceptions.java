package com.example.receiptApp.execeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class handleExceptions {
    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<String> emailDoesNotExist(EmailExistsException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PhoneNumberExistsException.class)
    public ResponseEntity<String> phoneNumberDoesExist(PhoneNumberExistsException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
