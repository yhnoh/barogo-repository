package com.example.barogo.exception.barogo;

import com.example.barogo.exception.BusinessException;

public class BarogoIllegalArgumentException extends BusinessException {

    public BarogoIllegalArgumentException(String msg) {
        super(msg);
    }
}
