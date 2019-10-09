package com.playtikaAcademy.w40kAddon.entities;

import lombok.*;

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
    @Column(name = "warrior_name", unique = true)
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
     * warriorSpeciality make influence for the obtaining skills
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "warrior_speciality", length = 16)
    private WarriorSpeciality warriorSpeciality;


    /**
     * Enum for warrior's specialities
     */
    public enum WarriorSpeciality {
        APOTHECARY, ASSAULT, LIBRARIAN, HEAVY_WEAPON, TACTICAL
    }
}
