package com.clelton.gl.exceptions;

public class LivroException extends  RuntimeException{
	
	private static final long serialVersionUID = 1L;
    public LivroException(String mensagem){
        super(mensagem);
    }

    public LivroException(String mensagem, Throwable cause){
        super(mensagem, cause);
    }
}
