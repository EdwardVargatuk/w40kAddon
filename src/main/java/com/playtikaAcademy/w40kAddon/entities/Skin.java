package com.playtikaAcademy.w40kAddon.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
public class Skin {

    @Id
    @GeneratedValue
    private final Integer id;
    private final String name;
    private final Double price;
    private final String url;
}
