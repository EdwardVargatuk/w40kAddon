package com.playtikaAcademy.w40kAddon.exceptions;

import lombok.Getter;

/**
 * 13.10.2019 23:05
 *
 * @author Edward
 */
@Getter
public class EntityNotFoundByParameterException extends RuntimeException {

    private final String param;

    public EntityNotFoundByParameterException(Class clazz, String param) {
        super("There is no " + clazz.getSimpleName() + " with: " + param);
        this.param = param;
    }
}
