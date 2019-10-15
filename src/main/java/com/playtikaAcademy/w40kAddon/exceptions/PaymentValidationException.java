package com.playtikaAcademy.w40kAddon.exceptions;

/**
 * 13.10.2019 23:23
 *
 * @author Edward
 */
public class PaymentValidationException extends RuntimeException {

    public PaymentValidationException(String message) {
        super(message);
    }

    public PaymentValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
