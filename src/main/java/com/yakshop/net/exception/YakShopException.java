package com.yakshop.net.exception;

public class YakShopException extends Exception {
   
	private static final long serialVersionUID = 1L;
	public YakShopException() { super(); }
    public YakShopException(String message) { super(message); }
    public YakShopException(String message, Throwable cause) { super(message, cause); }
    public YakShopException(Throwable cause) { super(cause); }
}
