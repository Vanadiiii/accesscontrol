package ru.dexsys.accesscontrol.exception;

public class NoSuchRoomException extends RuntimeException {
    public NoSuchRoomException() {
    }

    public NoSuchRoomException(String message) {
        super(message);
    }

    public NoSuchRoomException(String message, Throwable cause) {
        super(message, cause);
    }

    public static NoSuchRoomException init(int keyId) {
        return new NoSuchRoomException("There are no such room with id #" + keyId + " in repository");
    }
}
