package com.jd.panda.dao.base;

public class DaoExecException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DaoExecException() {
        super();
    }

    public DaoExecException(String message) {
        super(message);
    }

    public DaoExecException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoExecException(Throwable cause) {
        super(cause);
    }
}