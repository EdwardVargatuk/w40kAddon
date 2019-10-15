package com.playtikaAcademy.w40kAddon.service.addon;

import com.playtikaAcademy.w40kAddon.entities.Skill;
import com.playtikaAcademy.w40kAddon.entities.Warrior;
import com.playtikaAcademy.w40kAddon.entities.WarriorSpeciality;

import java.util.Set;

/**
 * 07.10.2019 21:56
 *
 * @author Edward
 */
public interface WarriorService {

    int getUpdatedLevel(long experience, int level);

    double getUpdatedAttack(Warrior warrior, String weaponName);

    double getUpdatedDefence(Warrior warrior, String armorName);

    Set<Skill> getActualSkills(Warrior warrior);

    double getDefaultDefence(WarriorSpeciality warriorSpeciality);

    double getDefaultAttack(WarriorSpeciality warriorSpeciality);
}
