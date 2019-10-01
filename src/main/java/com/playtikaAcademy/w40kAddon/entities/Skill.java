package com.playtikaAcademy.w40kAddon.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * 28.09.2019 23:41
 *
 * @author Edward
 */

@Data
@Entity
public class Skill {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer level;

    @ManyToMany
    private List<Warrior> warriors;
    //TODO: chose realization
//    private List<String> abilities;
//    private String description;
}
