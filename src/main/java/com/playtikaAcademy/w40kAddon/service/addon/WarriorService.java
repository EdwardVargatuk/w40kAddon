package com.playtikaAcademy.w40kAddon.service.addon;

import com.playtikaAcademy.w40kAddon.entities.Armor;
import com.playtikaAcademy.w40kAddon.entities.Weapon;

/**
 * 07.10.2019 21:56
 *
 * @author Edward
 */
public interface WarriorService {

    int getUpdatedLevel(long experience, int level);

    double getUpdatedAttack(Weapon weapon);

    double getUpdatedDefence(Armor armor);
}
