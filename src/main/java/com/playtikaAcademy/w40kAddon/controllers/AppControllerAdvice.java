package com.playtikaAcademy.w40kAddon.controllers;

import com.playtikaAcademy.w40kAddon.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * 13.10.2019 15:50
 *
 * @author Edward
 */
@Slf4j
@ControllerAdvice(basePackageClasses = AppControllerAdvice.class)
@RestController
@Order(HIGHEST_PRECEDENCE)
public class AppControllerAdvice extends ResponseEntityExceptionHandler {

    private static final String INTERNAL_SERVER_ERROR = "Internal server error";
    private static final String ENTITY_NOT_FOUND_BY_NAME = "Entity Not Found By Name";
    private static final String PAYMENT_VALIDATION_ERROR = "Payment Validation problems";
    private static final String WARRIOR_ALREADY_HAVE_EQUIPMENT = "Warrior Already Have that Equipment";
    private static final String INACCESSIBLE_ACTIONS = "You are trying to access actions that you did not meet the required requirements";
    private static final String NON_UNIQUE_WARRIOR_NAME = "You are trying to create warrior with non unique name!";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VndErrors> onUnexpectedServiceException(Exception exception) {
        log.error("Unhandled exception.", exception);
        return handlerFail(exception, HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundByParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VndErrors> onEntityNotFoundByNameException(Exception exception) {
        log.error("Unhandled exception.", exception);
        return handlerFail(exception, HttpStatus.NOT_FOUND, ENTITY_NOT_FOUND_BY_NAME);
    }

    @ExceptionHandler(PaymentValidationException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VndErrors> onPaymentValidationException(Exception exception) {
        log.error("Unhandled exception.", exception);
        return handlerFail(exception, HttpStatus.BAD_REQUEST, PAYMENT_VALIDATION_ERROR);
    }

    @ExceptionHandler(WarriorAlreadyHaveEquipmentException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VndErrors> onAddEquipmentThatWarriorAlreadyContainsException(Exception exception) {
        log.error("Unhandled exception.", exception);
        return handlerFail(exception, HttpStatus.BAD_REQUEST, WARRIOR_ALREADY_HAVE_EQUIPMENT);
    }

    @ExceptionHandler(InaccessibleRequestException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VndErrors> onTryToAccessNotAllowedActionsRequestException(Exception exception) {
        log.error("Unhandled exception.", exception);
        return handlerFail(exception, HttpStatus.METHOD_NOT_ALLOWED, INACCESSIBLE_ACTIONS);
    }

    @ExceptionHandler(NonUniqueWarriorNameException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<VndErrors> onPutNonUniqueNameForWarriorException(Exception exception) {
        log.error("Unhandled exception.", exception);
        return handlerFail(exception, HttpStatus.INTERNAL_SERVER_ERROR, NON_UNIQUE_WARRIOR_NAME);
    }

    private ResponseEntity<VndErrors> handlerFail(Exception exception, HttpStatus httpStatus, String logRef) {
        String message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new VndErrors(logRef, message), httpStatus);
    }
}
