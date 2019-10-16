package com.playtikaAcademy.w40kAddon.controllers;

import com.playtikaAcademy.w40kAddon.addon.games.Quest;
import com.playtikaAcademy.w40kAddon.entities.*;
import com.playtikaAcademy.w40kAddon.service.MainGameService;
import com.playtikaAcademy.w40kAddon.service.addon.PseudoGooglePayService;
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


    private final MainGameService mainGameService;

    private final PseudoGooglePayService pseudoGooglePayService;

    @Autowired
    public GameController(MainGameService mainGameService, PseudoGooglePayService pseudoGooglePayService) {
        this.mainGameService = mainGameService;
        this.pseudoGooglePayService = pseudoGooglePayService;
    }

    @PutMapping("warrior/new/")
    public Warrior createNewWarrior(@RequestParam("user-id") int userId,
                                    @RequestParam("warrior-name") String warriorName,
                                    @RequestBody WarriorSpeciality warriorSpeciality) {
        return mainGameService.createNewWarrior(userId, warriorName, warriorSpeciality);
    }

    @PostMapping("warrior/battle/")
    public Warrior makeInvisibleBattle(@RequestParam("name") String warriorName) {
        return mainGameService.getCharacterAfterBattle(warriorName);
    }

    @PostMapping("warrior/weapon/change/")
    public Warrior changeWeapon(@RequestParam("warriorName") String warriorName,
                                @RequestParam("weaponName") String weaponName) {
        return mainGameService.getWarriorAfterChangeWeapon(warriorName, weaponName);
    }

    @PostMapping("warrior/armor/change/")
    public Warrior changeArmor(@RequestParam("name") String warriorName,
                               @RequestParam("armorName") String armorName) {
        return mainGameService.getWarriorAfterChangeArmor(warriorName, armorName);
    }

    @PutMapping("warrior/weapon/add/")
    public Warrior addWeapon(@RequestParam("warriorName") String warriorName,
                             @RequestBody Weapon weapon) {
        return mainGameService.addWeaponToWarrior(warriorName, weapon);
    }

    @PutMapping("warrior/armor/add/")
    public Warrior addWeapon(@RequestParam("warriorName") String warriorName,
                             @RequestBody Armor armor) {
        return mainGameService.addArmorToWarrior(warriorName, armor);
    }

    @GetMapping("user/{id}")
    public User getUser(@PathVariable("id") int id) {
        return mainGameService.getUser(id);
    }

    @PostMapping("store/buySkin/")
    public ResponseEntity<User> buySkin(@RequestParam("userName") String userName, @RequestParam("warriorName") String warriorName,
                                        @RequestParam("skinName") String skinName) {
        return ResponseEntity.status(HttpStatus.OK).body(pseudoGooglePayService.buySkin(skinName, warriorName, userName));
    }

    @GetMapping("intro/chapter/{chapterNumber}/")
    public String getIntroduction(@PathVariable("chapterNumber") int chapterNumber) {
        return mainGameService.getIntroductionDescription(chapterNumber);
    }

    @GetMapping("console/")
    public String readFromConsole(){
        Quest quest = new Quest();
        quest.readFromConsole();
        return "ok";
    }
//    @PostMapping("addon/firstBonusGame/")
//    public ResponseEntity<User> firstBonusGame(@RequestParam("userName") String userName, @RequestParam("warriorName") String warriorName) {
//        return gameService.startNewGame();
//    }
}
