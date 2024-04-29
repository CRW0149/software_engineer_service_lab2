package org.example.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private  String message;
    private  Integer code;
    private T data;
    public static <T>Result<T> success(T data,String message){
        Result<T> result=new Result<T>();
        result.data=data;
        result.message=message;
        result.code=200;
        return result;
    }
}
