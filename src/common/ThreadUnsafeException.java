package common;

public class ThreadUnsafeException extends RuntimeException{
    public ThreadUnsafeException() {
        super();
    }

    public ThreadUnsafeException(String message) {
        super(message);
    }

    public ThreadUnsafeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThreadUnsafeException(Throwable cause) {
        super(cause);
    }

    public ThreadUnsafeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
