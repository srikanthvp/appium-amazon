package com.qa.Exceptions;

public class loginFailedException extends Throwable {

    // On failed login throws new exception
    public void loginFailedException() throws Exception {
        throw new Exception();
    }

}
