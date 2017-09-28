package com.geocentric.foundation.net;


public class NoNetworkActivityException extends Exception {

    private static final long serialVersionUID = 1L;

    public NoNetworkActivityException() {
        super();
    }

    public NoNetworkActivityException(String message) {
        super(message);
    }

}
