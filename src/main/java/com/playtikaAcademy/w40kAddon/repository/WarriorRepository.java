package com.playtikaAcademy.w40kAddon.repository;

import com.playtikaAcademy.w40kAddon.entities.Warrior;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 30.09.2019 20:41
 *
 * @author Edward
 */
public interface WarriorRepository extends JpaRepository<Warrior, Integer> {

    Optional<Warrior> findByWarriorName(String name);

}
