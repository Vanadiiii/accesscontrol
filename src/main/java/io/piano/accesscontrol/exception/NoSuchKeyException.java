package io.piano.accesscontrol.exception;

public class NoSuchKeyException extends RuntimeException {
    public NoSuchKeyException() {
    }

    public NoSuchKeyException(String message) {
        super(message);
    }

    public NoSuchKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public static NoSuchKeyException init(int keyId) {
        return new NoSuchKeyException("There are no such user with key #" + keyId + " in repository");
    }
}
