package com.playtikaAcademy.w40kAddon.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * 27.09.2019 22:19
 *
 * @author Edward
 */
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class User {

    @Id
    @GeneratedValue
    private final Integer id;

    @Column(name = "user_name")
    private final String userName;
    private final String password;
    private final String email;
    private final Double balance;

    @OneToMany
    @JoinColumn(name="user_id")
    private final List<Warrior> warriors;

}
