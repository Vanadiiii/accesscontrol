package ru.dexsys.accesscontrol.exception;

import ru.dexsys.accesscontrol.domain.IChecking;

public class CheckProcessException extends RuntimeException {
    public CheckProcessException() {
    }

    public CheckProcessException(String message) {
        super(message);
    }

    public CheckProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public static <T extends IChecking> CheckProcessException init(int keyId) {
        return new CheckProcessException("Please, check access for user #" + keyId);
    }
}
