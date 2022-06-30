package com.example.barogo.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private String msg;

    public BusinessException(String msg) {
        super(msg);
        this.msg = msg;
    }




}
