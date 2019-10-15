package com.playtikaAcademy.w40kAddon.service.addon;

import com.playtikaAcademy.w40kAddon.entities.*;
import com.playtikaAcademy.w40kAddon.repository.SkillRepository;
import com.playtikaAcademy.w40kAddon.repository.WarriorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 07.10.2019 21:52
 *
 * @author Edward
 */
public class WarriorActualizer implements WarriorService {

    private static final int FIRST_RATE = 1;
    private static final int SECOND_RATE = 2;
    private static final int THIRD_RATE = 3;
    private static final int FOURTH_RATE = 4;

    @Autowired
    WarriorRepository warriorRepository;

    @Autowired
    SkillRepository skillRepository;

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

    /**
     * @param warrior    target needed to update param
     * @param weaponName for calculate attack
     * @return updated warrior attack/default attack for empty weapon list
     */
    @Override
    public double getUpdatedAttack(Warrior warrior, String weaponName) {
        List<Weapon> weapons = warrior.getWeapons();
        if (weapons.size() > 0) {
            int index = 0;
            for (Weapon weapon : weapons) {
                if (weapon.getName().equals(weaponName)) {
                    index = weapons.indexOf(weapon);
                    break;
                }
            }
            double defaultAttack = getDefaultAttack(warrior.getWarriorSpeciality());
            return defaultAttack + (defaultAttack * (weapons.get(index).getPower() / 100));
        } else
            return warrior.getAttack();
    }

    /**
     * @param warrior   target needed to update param
     * @param armorName for calculate defence
     * @return updated warrior defence/default defence for empty armor list
     */
    @Override
    public double getUpdatedDefence(Warrior warrior, String armorName) {
        List<Armor> armors = warrior.getArmors();
        if (armors.size() > 0) {
            int index = 0;
            for (Armor armor : armors) {
                if (armor.getName().equals(armorName)) {
                    index = armors.indexOf(armor);
                    break;
                }
            }
            double defaultDefence = getDefaultDefence(warrior.getWarriorSpeciality());
            return defaultDefence + (defaultDefence * (warrior.getArmors().get(index).getPower() / 100));
        } else
            return warrior.getDefence();
    }

    /**
     * @param warrior target needed to update param
     * @return actual skills according warrior level
     */
    @Override
    public Set<Skill> getActualSkills(Warrior warrior) {
        Optional<Skill> optionalSkill = getSkillAccordingWarriorLevelAndSpeciality(warrior);
        optionalSkill.ifPresent(skill -> warrior.getSkills().add(skill));
        return warrior.getSkills();
    }


    /**
     * @param warrior target for search
     * @return Optional of skill
     */
    private Optional<Skill> getSkillAccordingWarriorLevelAndSpeciality(Warrior warrior) {
        switch (warrior.getLevel()) {
            case 3:
                return skillRepository.findByRateAndWarriorSpeciality(FIRST_RATE, warrior.getWarriorSpeciality());
            case 5:
                return skillRepository.findByRateAndWarriorSpeciality(SECOND_RATE, warrior.getWarriorSpeciality());
            case 7:
                return skillRepository.findByRateAndWarriorSpeciality(THIRD_RATE, warrior.getWarriorSpeciality());
            case 15:
                return skillRepository.findByRateAndWarriorSpeciality(FOURTH_RATE, warrior.getWarriorSpeciality());
        }
        return Optional.empty();
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

    /**
     * @return default attack according warriorSpeciality
     */
    @Override
    public double getDefaultAttack(WarriorSpeciality warriorSpeciality) {
        switch (warriorSpeciality) {
            case APOTHECARY:
                return 5.0;
            case ASSAULT:
                return 22.2;
            case LIBRARIAN:
                return 11.5;
            case HEAVY_WEAPON:
                return 34.7;
            case TACTICAL:
                return 15.4;
            default:
                return 0.0;
        }
    }

    /**
     * @return default defence according warriorSpeciality
     */
    @Override
    public double getDefaultDefence(WarriorSpeciality warriorSpeciality) {
        switch (warriorSpeciality) {
            case APOTHECARY:
                return 9.0;
            case ASSAULT:
                return 22.0;
            case LIBRARIAN:
                return 15.5;
            case HEAVY_WEAPON:
                return 55.5;
            case TACTICAL:
                return 18.4;
            default:
                return 0.0;
        }
    }
}
