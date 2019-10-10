package com.playtikaAcademy.w40kAddon.service.addon;

import com.playtikaAcademy.w40kAddon.entities.Skill;
import com.playtikaAcademy.w40kAddon.entities.Warrior;
import com.playtikaAcademy.w40kAddon.entities.WarriorSpeciality;
import com.playtikaAcademy.w40kAddon.repository.SkillRepository;
import com.playtikaAcademy.w40kAddon.repository.WarriorRepository;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Override
    public double getUpdatedAttack(Warrior warrior) {
        if (warrior.getWeapon() != null) {
            return getDefaultAttack(warrior.getWarriorSpeciality()) * warrior.getWeapon().getPower() / 100;
        } else
            return warrior.getAttack();
    }

    @Override
    public double getUpdatedDefence(Warrior warrior) {
        if (warrior.getArmor() != null) {
            return getDefaultDefence(warrior.getWarriorSpeciality()) * warrior.getArmor().getPower() / 100;
        } else
            return warrior.getDefence();
    }

    @Override
    public Set<Skill> getActualSkills(Warrior warrior) {
        Optional<Skill> optionalSkill = getSkillAccordingWarriorLevelAndSpeciality(warrior);
        optionalSkill.ifPresent(skill -> warrior.getSkills().add(skill));
        return warrior.getSkills();
    }


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
