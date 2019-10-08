package com.playtikaAcademy.w40kAddon.repository;

import com.playtikaAcademy.w40kAddon.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 30.09.2019 7:47
 *
 * @author Edward
 */
public interface UserRepository extends JpaRepository<User, Integer> {

   Optional<User> findByUserName(String pass);
}
