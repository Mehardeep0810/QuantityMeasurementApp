package com.quantity.measurement.exception;

/**
 * UC15-compliant QuantityMeasurementException.
 * Centralizes all quantity-measurement-related errors.
 * Extends RuntimeException (unchecked) for clean propagation.
 */
public class QuantityMeasurementException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final String errorCode;

    // Message-only constructor
    public QuantityMeasurementException(String message) {
        super(message);
        this.errorCode = null;
    }

    // Message + cause constructor
    public QuantityMeasurementException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
    }

    // Message + errorCode constructor
    public QuantityMeasurementException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "QuantityMeasurementException{" +
                "message=" + getMessage() +
                (errorCode != null ? ", code=" + errorCode : "") +
                '}';
    }
}
