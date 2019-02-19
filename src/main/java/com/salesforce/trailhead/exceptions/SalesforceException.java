package com.salesforce.trailhead.exceptions;

public class SalesforceException extends RuntimeException {

    public SalesforceException() {
        super();
    }

    public SalesforceException(String message) {
        super(message);
    }

    public SalesforceException(String message, Throwable cause) {
        super(message, cause);
    }
}
