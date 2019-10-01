package com.playtikaAcademy.w40kAddon.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * 27.09.2019 22:19
 *
 * @author Edward
 */
@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_name")
    private String userName;
    private String password;
    private String email;
    private Double balance;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Warrior> warriors;

}
