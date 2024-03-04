package com.clelton.gl.exceptions;

public class LivroException extends  RuntimeException{
    public LivroException(String mensagem){
        super(mensagem);
    }

    public LivroException(String mensagem, Throwable cause){
        super(mensagem, cause);
    }
}
