package com.example.barogo;

import com.example.barogo.exception.BusinessException;
import com.example.barogo.exception.barogo.BarogoAuthorizationException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {
    private boolean result;
    private int status;
    private T data;
    private String msg = "";

    public static <T> ApiResponse<T> success(T data, String msg){
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.result = true;
        apiResponse.status = HttpStatus.OK.value();
        apiResponse.data = data;
        apiResponse.msg = msg;
        return apiResponse;
    }

    public static <T> ApiResponse<T> error(BusinessException e){
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.result = false;
        if(e instanceof BarogoAuthorizationException){
            apiResponse.status = HttpStatus.UNAUTHORIZED.value();
        }else{
            apiResponse.status = HttpStatus.BAD_REQUEST.value();
        }

        apiResponse.msg = e.getMessage();
        return apiResponse;
    }
}
