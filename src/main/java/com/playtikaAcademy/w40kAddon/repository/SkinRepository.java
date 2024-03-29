package com.playtikaAcademy.w40kAddon.repository;

import com.playtikaAcademy.w40kAddon.entities.Skin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 02.10.2019 11:45
 *
 * @author Edward
 */
public interface SkinRepository extends JpaRepository<Skin, Integer> {

    Optional<Skin> findByName(String name);
}
