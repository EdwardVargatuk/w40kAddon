package com.playtikaAcademy.w40kAddon.exceptions;

import lombok.Getter;

/**
 * 14.10.2019 20:48
 *
 * @author Edward
 */
@Getter
public class WarriorAlreadyHaveEquipmentException extends RuntimeException {

    private final String equipment;

    public WarriorAlreadyHaveEquipmentException(String equipmentName) {
        super("Warrior already have such equipment: "  + equipmentName);
        this.equipment = equipmentName;
    }
}
