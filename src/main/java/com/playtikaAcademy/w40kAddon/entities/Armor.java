package com.playtikaAcademy.w40kAddon.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * 28.09.2019 23:40
 *
 * @author Edward
 */
@Data
@Entity
public class Armor {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Double power;

    @OneToMany(mappedBy = "armor", cascade = CascadeType.PERSIST)
    private List<Warrior> warriorList;
}
