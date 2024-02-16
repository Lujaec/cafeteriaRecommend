package com.example.cafeteriarecommend.global.exception;

import com.example.cafeteriarecommend.global.error.ErrorCode;

public class CustomException extends RuntimeException{

    private final ErrorCode code;

    public CustomException(ErrorCode code, String message){
        super(code.getMessage() + "("+message + ")");
        this.code = code ;
    }
    public CustomException(ErrorCode code){
        super(code.getMessage());
        this.code = code;
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public ErrorCode getCode(){
        return this.code;
    }
}
