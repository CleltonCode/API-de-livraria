package com.clelton.gl.exceptions;

public class AutorException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AutorException(String message) {super(message); }

    public AutorException(String message, Throwable cause) {super(message, cause);}
}
