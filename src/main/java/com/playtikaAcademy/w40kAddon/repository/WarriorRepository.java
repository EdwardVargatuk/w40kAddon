package com.playtikaAcademy.w40kAddon.repository;

import com.playtikaAcademy.w40kAddon.entities.Warrior;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 30.09.2019 20:41
 *
 * @author Edward
 */
public interface WarriorRepository extends JpaRepository<Warrior, Integer> {

    Warrior findByWarriorName(String name);

}
