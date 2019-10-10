package com.playtikaAcademy.w40kAddon.controllers;

import com.playtikaAcademy.w40kAddon.entities.User;
import com.playtikaAcademy.w40kAddon.entities.Warrior;
import com.playtikaAcademy.w40kAddon.service.MainGameService;
import com.playtikaAcademy.w40kAddon.service.addon.PseudoGooglePayService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final PseudoGooglePayService pseudoGooglePayService;

    @Autowired
    public GameController(MainGameService mainGameService, PseudoGooglePayService pseudoGooglePayService) {
        this.mainGameService = mainGameService;
        this.pseudoGooglePayService = pseudoGooglePayService;
    }

    @PutMapping("warrior/new/{warrior-name}/{speciality}/")
    public Warrior createNewWarrior(@PathVariable("warrior-name") String warriorName,
                                    @PathVariable("speciality") int warriorSpeciality) {
        return mainGameService.createNewWarrior(warriorName, warriorSpeciality);
    }

    @PostMapping("battle/")
    public Warrior makeInvisibleBattle(@RequestParam("name") String warriorName) {
        return mainGameService.getCharacterAfterBattle(warriorName);
    }

    @GetMapping("user/{id}")
    public User getUser(@PathVariable("id") int id) {
        return mainGameService.getUser(id).isPresent() ? mainGameService.getUser(id).get() : User.builder().build();
    }

    @PostMapping("store/buySkin/{userName}/{warriorName}/{skinName}/")
    public ResponseEntity<User> buySkin(@PathVariable("userName") String userName, @PathVariable("warriorName") String warriorName,
                                        @PathVariable("skinName") String skinName) {
        return ResponseEntity.status(HttpStatus.OK).body(pseudoGooglePayService.buySkin(skinName, warriorName, userName));
//        return (mainGameService.buySkin(skinName, warriorName,userName));
    }

    @GetMapping("intro/chapter/{chapterNumber}/")
    public String getIntroduction(@PathVariable("chapterNumber") int chapterNumber) {
        return mainGameService.getIntroductionDescription(chapterNumber);
    }
}
