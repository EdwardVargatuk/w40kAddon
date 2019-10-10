package com.playtikaAcademy.w40kAddon.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

import javax.persistence.*;

/**
 * 28.09.2019 23:41
 *
 * @author Edward
 */

@Data
@Builder
@Wither
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Skill {

    @Id
    @GeneratedValue
    private final Integer id;
    private final String name;
    private final Integer rate;
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "warrior_speciality", length = 16)
    private final WarriorSpeciality warriorSpeciality;
}
