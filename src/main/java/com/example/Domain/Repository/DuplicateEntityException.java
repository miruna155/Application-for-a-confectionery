package com.example.Domain.Repository;

public class DuplicateEntityException extends Exception{
    public DuplicateEntityException(String message) {
        super(message);
    }
}
