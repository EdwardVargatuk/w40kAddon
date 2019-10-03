package com.playtikaAcademy.w40kAddon.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * 28.09.2019 23:40
 *
 * @author Edward
 */

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Warrior implements Cloneable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private final Integer id;
    @Column(name = "warrior_name")
    private final String warriorName;
    private final Double balance;
    private final Long experience;
    private final Integer level;
    private final Double attack;
    private final Double defence;
    private final Integer agility;

    @ManyToMany
    @JoinTable
    private final List<Skill> skills;

    @ManyToOne
    @JoinColumn(name = "weapon_id")
    private final Weapon weapon;

    @ManyToOne
    @JoinColumn(name = "armor_id")
    private final Armor armor;

    @ManyToOne
    @JoinColumn(name = "skin_id")
    private final Skin skin;


    /**
     * @param experience of Warrior
     * @param level      of Warrior
     * @return level according to picked experience
     */
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
     * calc by pow of warrior lvl
     *
     * @param level of warrior
     * @return how much experience need to level Up
     */
    private long needExperienceForLvlUp(int level) {
        return (long) Math.pow(level, 2) * 1000;
    }
}
