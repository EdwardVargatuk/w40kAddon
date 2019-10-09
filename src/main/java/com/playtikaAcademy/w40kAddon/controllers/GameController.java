package com.playtikaAcademy.w40kAddon.controllers;

import com.playtikaAcademy.w40kAddon.entities.User;
import com.playtikaAcademy.w40kAddon.entities.Warrior;
import com.playtikaAcademy.w40kAddon.service.MainGameService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 30.09.2019 20:50
 *
 * @author Edward
 */
@RequestMapping("game/")
@RestController
public class GameController {


    private static final Logger logger = LogManager.getLogger(GameController.class.getName());

    private final MainGameService mainGameService;

    @Autowired
    public GameController(MainGameService mainGameService) {
        this.mainGameService = mainGameService;
    }

    @PutMapping("warrior/new/{warrior-name}")
    public Warrior createNewWarrior(@PathVariable("warrior-name") String warriorName) {
        Warrior warrior = Warrior.builder()
                .warriorName(warriorName)
                .level(1)
                .experience(0L)
                .balance(2.0)
                .agility(0)
                .attack(10.0)
                .defence(5.0)
                .build();
        logger.debug("New warrior created " + warrior);
        return mainGameService.getWarriorRepository().save(warrior);
    }

    @PostMapping("battle/")
    public Warrior makeInvisibleBattle(@RequestParam("name") String warriorName) {
        return mainGameService.getCharacterAfterBattle(warriorName);
    }

    @GetMapping("user/{id}")
    public User getUser(@PathVariable("id") int id) {
        return mainGameService.getUser(id).isPresent() ? mainGameService.getUser(id).get() : User.builder().build();
    }

    @GetMapping("user/store/buySkin/{userName}/{warriorName}/{skinName}/")
    public ResponseEntity<User> buySkin(@PathVariable("userName") String userName, @PathVariable("warriorName") String warriorName,
                                  @PathVariable("skinName") String skinName) {
        return ResponseEntity.status(HttpStatus.OK).body(mainGameService.buySkin(skinName, warriorName,userName));
//        return (mainGameService.buySkin(skinName, warriorName,userName));
    }
}
