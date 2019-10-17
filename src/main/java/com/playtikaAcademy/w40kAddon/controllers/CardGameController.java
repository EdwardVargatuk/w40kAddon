package com.playtikaAcademy.w40kAddon.controllers;

import com.playtikaAcademy.w40kAddon.entities.Warrior;
import com.playtikaAcademy.w40kAddon.service.addon.TwentyOneGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 16.10.2019 19:17
 *
 * @author Edward
 */
@RequestMapping("game/21/")
@RestController
public class CardGameController {

    private final TwentyOneGameService twentyOneGameService;

    @Autowired
    public CardGameController(TwentyOneGameService twentyOneGameService) {
        this.twentyOneGameService = twentyOneGameService;
    }

    @GetMapping("new-game/")
    public String startNewGame(@RequestParam String warriorName) {
        return twentyOneGameService.startNewGame(warriorName);

    }

    @PostMapping("bonuses/accept/")
    public ResponseEntity<Warrior> acceptBonuses(@RequestParam String warriorName) {
        return ResponseEntity.ok().body(twentyOneGameService.acceptWarriorBonuses(warriorName));
    }

    @PostMapping("choice-action/")
    public String handleUserChoice(@RequestParam("user-choice") int userChoice) {
        return twentyOneGameService.singlePlayerGameStep(userChoice);
    }
}
