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
public class Skin {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Double price;
    private String url;

    @OneToMany(mappedBy = "skin", cascade = CascadeType.PERSIST)
    private List<Warrior> warriorList;


}
