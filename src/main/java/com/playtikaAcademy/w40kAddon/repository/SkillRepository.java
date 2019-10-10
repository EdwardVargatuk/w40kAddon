package com.playtikaAcademy.w40kAddon.repository;

import com.playtikaAcademy.w40kAddon.entities.Skill;
import com.playtikaAcademy.w40kAddon.entities.WarriorSpeciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 02.10.2019 11:44
 *
 * @author Edward
 */
public interface SkillRepository extends JpaRepository<Skill, Integer> {

    Optional<Skill> findByRateAndWarriorSpeciality(Integer rate, WarriorSpeciality warriorSpeciality);

}
