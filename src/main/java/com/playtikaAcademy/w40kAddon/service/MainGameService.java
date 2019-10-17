package com.playtikaAcademy.w40kAddon.service;

import com.playtikaAcademy.w40kAddon.entities.*;
import com.playtikaAcademy.w40kAddon.exceptions.EntityNotFoundByParameterException;
import com.playtikaAcademy.w40kAddon.exceptions.NonUniqueWarriorNameException;
import com.playtikaAcademy.w40kAddon.exceptions.WarriorAlreadyHaveEquipmentException;
import com.playtikaAcademy.w40kAddon.repository.SkillRepository;
import com.playtikaAcademy.w40kAddon.repository.SkinRepository;
import com.playtikaAcademy.w40kAddon.repository.UserRepository;
import com.playtikaAcademy.w40kAddon.repository.WarriorRepository;
import com.playtikaAcademy.w40kAddon.service.addon.IntroductionDescriptor;
import com.playtikaAcademy.w40kAddon.service.addon.WarriorService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 30.09.2019 20:53
 *
 * @author Edward
 */

@Data
@Slf4j
public class MainGameService {

    private final Random random = new Random();

    private static final long LOWER_BOUND = 300L;
    private static final long UPPER_BOUND = 900L;

    @Autowired
    WarriorRepository warriorRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    SkinRepository skinRepository;

    @Autowired
    IntroductionDescriptor introductionDescriptor;

    @Autowired
    WarriorService warriorService;


    /**
     * @param warriorName for get warrior if exist
     * @return warrior of throw EntityNotFoundByParameterException
     */
    public Warrior getWarriorByName(String warriorName) {
        Optional<Warrior> warriorOptional = warriorRepository.findByWarriorName(warriorName);
        if (warriorOptional.isPresent()) {
            return warriorOptional.get();
        } else throw new EntityNotFoundByParameterException(Warrior.class, "name: " + warriorName);
    }

    /**
     * update warrior in database
     * simulate game process, when after each battle player can obtain experience
     *
     * @param name for get warrior
     * @return updated warrior
     */
    public Warrior getCharacterAfterBattle(String name) {
        Warrior warrior = getWarriorByName(name);
        long randomExperience = ThreadLocalRandom.current().nextLong(LOWER_BOUND, UPPER_BOUND);
        int currentLevel = warriorService.getUpdatedLevel(warrior.getExperience() + randomExperience, warrior.getLevel());
        Warrior newWarrior = warrior.withLevel(currentLevel)
                .withExperience(warrior.getExperience() + randomExperience)
                .withSkills(warriorService.getActualSkills(warrior.withLevel(currentLevel)));
        return warriorRepository.save(newWarrior);
    }


    /**
     * @param id for find user
     * @return user of throw EntityNotFoundByParameterException
     */
    public User getUser(int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else throw new EntityNotFoundByParameterException(User.class, "id: " + id);
    }


    /**
     * @param warriorName to update
     * @param weaponName  to change
     * @return updated warrior
     */
    public Warrior getWarriorAfterChangeWeapon(String warriorName, String weaponName) {
        Warrior warrior = getWarriorByName(warriorName);
        return warriorRepository.save(warrior.withAttack(warriorService.getUpdatedAttack(warrior, weaponName)));
    }


    /**
     * @param warriorName target to add weapon
     * @param weapon      to add to warrior if he not contains it
     * @return updated warrior
     */
    public Warrior addWeaponToWarrior(String warriorName, Weapon weapon) {
        Warrior warrior = getWarriorByName(warriorName);
        List<Weapon> weaponList = warrior.getWeapons();
        if (weaponList.indexOf(weapon) > -1) {
            throw new WarriorAlreadyHaveEquipmentException(weapon.getName());
        } else {
            List<Weapon> weapons = warrior.getWeapons();
            weapons.add(weapon);
            Warrior updatedWarrior = warrior.withWeapons(weapons).withAttack(warriorService.getUpdatedAttack(warrior, weapon.getName()));
            return warriorRepository.save(updatedWarrior);
        }
    }

    /**
     * @param warriorName to update
     * @param armorName   to change
     * @return updated warrior
     */
    public Warrior getWarriorAfterChangeArmor(String warriorName, String armorName) {
        Warrior warrior = getWarriorByName(warriorName);
        return warriorRepository.save(warrior.withDefence(warriorService.getUpdatedDefence(warrior, armorName)));
    }

    /**
     * @param warriorName target to add armor
     * @param armor       to add to warrior if he not contains it
     * @return updated warrior
     */
    public Warrior addArmorToWarrior(String warriorName, Armor armor) {
        Warrior warrior = getWarriorByName(warriorName);
        List<Armor> armors = warrior.getArmors();
        if (armors.indexOf(armor) > -1) {
            throw new WarriorAlreadyHaveEquipmentException(armor.getName());
        } else {
            List<Armor> warriorArmors = warrior.getArmors();
            warriorArmors.add(armor);
            Warrior updatedWarrior = warrior.withArmors(warriorArmors).withDefence(warriorService.getUpdatedDefence(warrior, armor.getName()));
            return warriorRepository.save(updatedWarrior);
        }
    }

    /**
     * to avoid getting from db every time
     *
     * @param user        as source
     * @param warriorName target name
     * @return Optional<Warrior>
     */
    public Optional<Warrior> getWarriorByNameForUser(User user, String warriorName) {
        List<Warrior> warriors = user.getWarriors();
        if (warriors.size() > 0) {
            for (Warrior warrior : warriors) {
                String name = warrior.getWarriorName();
                if (name.equals(warriorName)) {
                    return Optional.of(warrior);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * @param warriorName       unique, nick in game
     * @param warriorSpeciality on which depends warrior stats
     * @return persisted warrior
     */
    public Warrior createNewWarrior(int userId, String warriorName, WarriorSpeciality warriorSpeciality) {
        checkUniqueWarriorName(warriorName);
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User currentUser = optionalUser.get();
            Warrior warrior = Warrior.builder()
                    .warriorName(warriorName)
                    .level(1)
                    .experience(0L)
                    .balance(10.0)
                    .agility(0)
                    .attack(warriorService.getDefaultAttack(warriorSpeciality))
                    .defence(warriorService.getDefaultDefence(warriorSpeciality))
                    .warriorSpeciality(warriorSpeciality)
                    .build();
            log.debug("new warrior: " + warrior);
            List<Warrior> warriors = currentUser.getWarriors();
            warriors.add(warrior);
            User updatedUser = userRepository.save(currentUser.withWarriors(warriors));
            return updatedUser.getWarriors().get(updatedUser.getWarriors().size() - 1);
        } else throw new EntityNotFoundByParameterException(User.class, "id: " + userId);
    }

    /**
     * @param warriorName in w40k game must be unique for all game world
     */
    private void checkUniqueWarriorName(String warriorName) {
        List<Warrior> allWarriorsList = warriorRepository.findAllByWarriorName(warriorName);
        if (allWarriorsList.size() > 0) {
            throw new NonUniqueWarriorNameException("warrior with name " + warriorName + " already exist!");
        }
    }

    /**
     * @param chapterNumber by default max chapter number is 3
     * @return chapter description
     */
    public String getIntroductionDescription(int chapterNumber) {
        switch (chapterNumber) {
            case 1:
                return introductionDescriptor.getFirstChapter();
            case 2:
                return introductionDescriptor.getSecondChapter();
            case 3:
                return introductionDescriptor.getThirdChapter();
            default:
                return "There is no such chapter";
        }
    }
}
