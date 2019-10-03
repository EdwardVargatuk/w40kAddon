package com.playtikaAcademy.w40kAddon.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
public class Weapon {

    @Id
    @GeneratedValue
    private final Integer id;
    private final String name;
    /**
     * percent that added to Warrior's Attack
     */
    private final Double power;
}
