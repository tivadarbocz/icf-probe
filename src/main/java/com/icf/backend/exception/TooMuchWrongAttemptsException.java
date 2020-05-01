package com.icf.backend.exception;

public class TooMuchWrongAttemptsException extends RuntimeException {

    public TooMuchWrongAttemptsException() {
        super();
    }

    public TooMuchWrongAttemptsException(final String message) {
        super(message);
    }

    public TooMuchWrongAttemptsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TooMuchWrongAttemptsException(final Throwable cause) {
        super(cause);
    }

    protected TooMuchWrongAttemptsException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
