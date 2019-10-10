package com.playtikaAcademy.w40kAddon.service;

import com.playtikaAcademy.w40kAddon.entities.User;
import com.playtikaAcademy.w40kAddon.entities.Warrior;
import com.playtikaAcademy.w40kAddon.entities.WarriorSpeciality;
import com.playtikaAcademy.w40kAddon.repository.SkillRepository;
import com.playtikaAcademy.w40kAddon.repository.SkinRepository;
import com.playtikaAcademy.w40kAddon.repository.UserRepository;
import com.playtikaAcademy.w40kAddon.repository.WarriorRepository;
import com.playtikaAcademy.w40kAddon.addon.utils.PseudoGooglePay;
import com.playtikaAcademy.w40kAddon.service.addon.WarriorService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

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
    PseudoGooglePay pseudoGooglePay;

    @Autowired
    WarriorService warriorService;

    @Autowired
    Environment env;

    public Warrior getCharacterAfterBattle(String name) {
        Optional<Warrior> warriorOptional = warriorRepository.findByWarriorName(name);
        Warrior newWarrior = Warrior.builder().build();
        if (warriorOptional.isPresent()) {
            Warrior warrior = warriorOptional.get();
            long randomExperience = ThreadLocalRandom.current().nextLong(LOWER_BOUND, UPPER_BOUND);
            int currentLevel = warriorService.getUpdatedLevel(warrior.getExperience() + randomExperience, warrior.getLevel());
            newWarrior = warrior.withLevel(currentLevel)
                    .withExperience(warrior.getExperience() + randomExperience)
                    .withSkills(warriorService.getActualSkills(warrior.withLevel(currentLevel)));
            return warriorRepository.save(newWarrior);
        }
        return newWarrior;
    }


    public Optional<User> getUser(int id) {
        return userRepository.findById(id);
    }

//    public Warrior getWarriorAfterChangeWeapon(Warrior warrior) {
//        Warrior warrior1 = warrior.withAttack(warriorService.getUpdatedAttack(warrior));
//
//    }
//
//    public Warrior getWarriorAfterChangeArmor(Warrior warrior) {
//
//    }

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

    private Optional<WarriorSpeciality> getWarriorSpecialityAccordingItsNumber(int warriorSpeciality) {
        switch (warriorSpeciality) {
            case 1:
                return Optional.of(WarriorSpeciality.APOTHECARY);
            case 2:
                return Optional.of(WarriorSpeciality.ASSAULT);
            case 3:
                return Optional.of(WarriorSpeciality.LIBRARIAN);
            case 4:
                return Optional.of(WarriorSpeciality.HEAVY_WEAPON);
            case 5:
                return Optional.of(WarriorSpeciality.TACTICAL);
        }
        return Optional.empty();
    }

    public Warrior createNewWarrior(String warriorName, int warriorSpeciality) {
        Optional<WarriorSpeciality> specialityOptional = getWarriorSpecialityAccordingItsNumber(warriorSpeciality);
        //dummy to get default user that already login in system
        Optional<User> optionalUser = userRepository.findById(1);
        Warrior warrior;
        if (optionalUser.isPresent()) {
            User currentUser = optionalUser.get();
            warrior = Warrior.builder()
                    .warriorName(warriorName)
                    .level(1)
                    .experience(0L)
                    .balance(10.0)
                    .agility(0)
                    .attack(warriorService.getDefaultAttack(specialityOptional.orElse(WarriorSpeciality.ASSAULT)))
                    .defence(warriorService.getDefaultDefence(specialityOptional.orElse(WarriorSpeciality.ASSAULT)))
                    .warriorSpeciality(specialityOptional.orElse(WarriorSpeciality.ASSAULT))
                    .build();
            currentUser.getWarriors().add(warrior);
            userRepository.save(currentUser);
        } else warrior = Warrior.builder().build();

        return warrior;

    }

    public String getIntroductionDescription(int chapterNumber) {
        switch (chapterNumber) {
            case 1:
                return env.getProperty("introduction.chapter.first");
            case 2:
                return env.getProperty("introduction.chapter.second");
            case 3:
                return env.getProperty("introduction.chapter.third");
            default:
                return "There is no such chapter";
        }
    }
}
