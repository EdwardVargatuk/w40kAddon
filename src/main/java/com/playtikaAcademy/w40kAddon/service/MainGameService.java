package com.playtikaAcademy.w40kAddon.service;

import com.playtikaAcademy.w40kAddon.entities.Skin;
import com.playtikaAcademy.w40kAddon.entities.User;
import com.playtikaAcademy.w40kAddon.entities.Warrior;
import com.playtikaAcademy.w40kAddon.repository.SkillRepository;
import com.playtikaAcademy.w40kAddon.repository.SkinRepository;
import com.playtikaAcademy.w40kAddon.repository.UserRepository;
import com.playtikaAcademy.w40kAddon.repository.WarriorRepository;
import com.playtikaAcademy.w40kAddon.service.addon.PseudoGooglePayService;
import com.playtikaAcademy.w40kAddon.service.addon.WarriorActualizer;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

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
    PseudoGooglePayService pseudoGooglePayService;

    @Autowired
    WarriorActualizer warriorActualizer;

    public Warrior getCharacterAfterBattle(String name) {
        Optional<Warrior> warriorOptional = warriorRepository.findByWarriorName(name);
        Warrior newWarrior = Warrior.builder().build();
        if (warriorOptional.isPresent()) {
            Warrior warrior = warriorOptional.get();
            long randomExperience = ThreadLocalRandom.current().nextLong(LOWER_BOUND, UPPER_BOUND);
            int currentLevel = warriorActualizer.getUpdatedLevel(warrior.getExperience() + randomExperience, warrior.getLevel());
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
                    .warriorSpeciality(warrior.getWarriorSpeciality())
                    .build();
            return warriorRepository.save(newWarrior);
        }
        return newWarrior;
    }

    public Optional<User> getUser(int id) {
        return userRepository.findById(id);
    }


    /**
     * to avoid getting from db every time
     * @param user
     * @param warriorName
     * @return
     */
    private Optional<Warrior> getWarriorByNameForUser(User user, String warriorName) {
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

    public User buySkin(String skinName, String warriorName, String userName) {
        Optional<User> optionalUser = userRepository.findByUserName(userName);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Warrior> warriorOptional = getWarriorByNameForUser(optionalUser.get(), warriorName);
            Optional<Skin> skinOptional = skinRepository.findByName(skinName);
            if (skinOptional.isPresent() && warriorOptional.isPresent()) {
                Skin skin = skinOptional.get();
                Warrior warrior = warriorOptional.get();
                String result = interactWithClient(user, skin);
                if (!StringUtils.isEmpty(result) && result.equals("OK")) {
                    Warrior newWarrior = Warrior.builder()
                            .id(warrior.getId())
                            .warriorName(warrior.getWarriorName())
                            .level(warrior.getLevel())
                            .experience(warrior.getExperience())
                            .balance(warrior.getBalance())
                            .attack(warrior.getAttack())
                            .defence(warrior.getDefence())
                            .agility(warrior.getAgility())
                            .armor(warrior.getArmor())
                            .weapon(warrior.getWeapon())
                            .skills(warrior.getSkills())
                            .skin(skin)
                            .warriorSpeciality(warrior.getWarriorSpeciality())
                            .build();
                    List<Warrior> userWarriors = user.getWarriors();
                    userWarriors.set(userWarriors.indexOf(warrior), newWarrior);

                    User newUser = User.builder()
                            .id(user.getId())
                            .userName(user.getUserName())
                            .balance(user.getBalance() - skin.getPrice())
                            .email(user.getEmail())
                            .password(user.getPassword())
                            .warriors(userWarriors)
                            .build();
                    return userRepository.save(newUser);
                }
            }
        }
        return User.builder().build();
    }

    private String interactWithClient(User user, Skin skin) {
        try {
            Optional<JSONObject> isReadyToPayRequest = pseudoGooglePayService.getIsReadyToPayRequest();
            if (isReadyToPayRequest.isPresent()) {
                JSONObject paymentDataResult = pseudoGooglePayService.requestPayment(skin.getPrice());
                JSONObject result = pseudoGooglePayService.validateRequestResult(42, paymentDataResult.put("userBalance", user.getBalance()));
                return (String) result.get("result");
            }
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return "";
    }
}
