package com.example.receiptApp.execeptions;

public class EmailExistsException extends RuntimeException{
    public EmailExistsException(String message){super(message);}
}
