package com.clelton.gl.exceptions;

public class EditoraException extends RuntimeException{

	private static final long serialVersionUID = 1L;
    public  EditoraException(String message){ super (message);}
    public  EditoraException(String message, Throwable cause){ super (message, cause);}
}
