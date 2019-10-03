package com.playtikaAcademy.w40kAddon.service;

import com.playtikaAcademy.w40kAddon.entities.User;
import com.playtikaAcademy.w40kAddon.entities.Warrior;
import com.playtikaAcademy.w40kAddon.entities.Weapon;
import com.playtikaAcademy.w40kAddon.repository.UserRepository;
import com.playtikaAcademy.w40kAddon.repository.WarriorRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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

    @Autowired
    UserRepository userRepository;

    public Warrior getCharacterAfterBattle(String name) {
        Optional<Warrior> warriorOptional = warriorRepository.findByWarriorName(name);
        Warrior newWarrior = Warrior.builder().build();
        if (warriorOptional.isPresent()) {
            Warrior warrior = warriorOptional.get();
            long randomExperience = ThreadLocalRandom.current().nextLong(LOWER_BOUND, UPPER_BOUND);
            int currentLevel = warriorOptional.get().getUpdatedLevel(warrior.getExperience() + randomExperience, warrior.getLevel());
            newWarrior = Warrior.builder()
                    .id(warrior.getId())
                    .warriorName(warrior.getWarriorName())
                    .level(currentLevel)
                    .experience(warrior.getExperience() + randomExperience)
                    .balance(warrior.getBalance())
                    .attack(warrior.getAttack())
                    .defence(warrior.getDefence())
                    .agility(warrior.getAgility())
                    .armor(warrior.getArmor())
                    .weapon(warrior.getWeapon())
                    .skills(warrior.getSkills())
                    .skin(warrior.getSkin())
                    .build();
            return warriorRepository.save(newWarrior);
        }
        return newWarrior;
    }

    public Optional<User> getUser(int id) {
        return userRepository.findById(id);
    }
}
