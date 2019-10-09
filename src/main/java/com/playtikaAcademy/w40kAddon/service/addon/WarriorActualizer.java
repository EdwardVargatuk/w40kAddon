package com.playtikaAcademy.w40kAddon.service.addon;

import com.playtikaAcademy.w40kAddon.entities.Armor;
import com.playtikaAcademy.w40kAddon.entities.Weapon;

/**
 * 07.10.2019 21:52
 *
 * @author Edward
 */
public class WarriorActualizer  implements WarriorService{

    /**
     * @param experience of Warrior
     * @param level      of Warrior
     * @return level according to picked experience
     */
    @Override
    public int getUpdatedLevel(long experience, int level) {
        if (experience >= needExperienceForLvlUp(level)) {
            int newLvl = level + 1;
            long experienceLeft = experience - needExperienceForLvlUp(level);
            if (experienceLeft >= needExperienceForLvlUp(newLvl)) {
                return getUpdatedLevel(experienceLeft, newLvl);
            } else return newLvl;

        } else return level;
    }
    //TODO
    @Override
    public double getUpdatedAttack(Weapon weapon) {
        return 0;
    }

    //TODO
    @Override
    public double getUpdatedDefence(Armor armor) {
        return 0;
    }

    /**
     * calc by pow of warrior lvl
     *
     * @param level of warrior
     * @return how much experience need to level Up
     */
    private long needExperienceForLvlUp(int level) {
        return (long) Math.pow(level, 2) * 1000;
    }
}
