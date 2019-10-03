package com.playtikaAcademy.w40kAddon.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * 28.09.2019 23:41
 *
 * @author Edward
 */

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Skill {

    @Id
    @GeneratedValue
    private final Integer id;
    private final String name;
    private final Integer level;
    //TODO: chose realization
//    private List<String> abilities;
//    private String description;
}
