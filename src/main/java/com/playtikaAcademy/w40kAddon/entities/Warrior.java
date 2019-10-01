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
@NoArgsConstructor
public class Warrior {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    @Column(name = "warrior_name")
    private String warriorName;
    private Double balance;
    private Long experience;
    private Integer level;
    private Double attack;
    private Double defence;
    private Integer agility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "warriors")
    private List<Skill> skills;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weapon_id")
    private Weapon weapon;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "armor_id")
    private Armor armor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skin_id")
    private Skin skin;

}
