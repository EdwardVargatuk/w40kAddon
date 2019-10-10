package com.playtikaAcademy.w40kAddon.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

import javax.persistence.*;
import java.util.List;

/**
 * 27.09.2019 22:19
 *
 * @author Edward
 */
@Data
@Builder
@Wither
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Integer id;

    @Column(name = "user_name")
    private final String userName;
    private final String password;
    private final String email;
    private final Double balance;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private final List<Warrior> warriors;

}
