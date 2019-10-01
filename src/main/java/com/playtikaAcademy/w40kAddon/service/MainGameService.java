package com.playtikaAcademy.w40kAddon.service;

import com.playtikaAcademy.w40kAddon.entities.Warrior;
import com.playtikaAcademy.w40kAddon.repository.WarriorRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * 30.09.2019 20:53
 *
 * @author Edward
 */
@Data
public class MainGameService {

    private final Random random = new Random();

    private static final long LOWER_BOUND = 300L;
    private static final long UPPER_BOUND = 900L;

    @Autowired
    WarriorRepository warriorRepository;

    public Warrior getCharacterAfterBattle(String name) {
        Warrior warrior = warriorRepository.findByWarriorName(name);
        warrior.setExperience((long) (Math.random() * (UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND));
        return warriorRepository.save(warrior);
    }
}
