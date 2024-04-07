package org.bigcompany.exception;

public class CEONotFoundException extends RuntimeException {

    public CEONotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
