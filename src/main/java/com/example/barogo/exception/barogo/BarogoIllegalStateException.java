package com.example.barogo.exception.barogo;

import com.example.barogo.exception.BusinessException;

public class BarogoIllegalStateException extends BusinessException {
    public BarogoIllegalStateException(String msg) {
        super(msg);
    }
}
