package com.fortechcode.project.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
public class UsuarioException extends RuntimeException{
    public UsuarioException(String message){
        super(message);
    }


}


