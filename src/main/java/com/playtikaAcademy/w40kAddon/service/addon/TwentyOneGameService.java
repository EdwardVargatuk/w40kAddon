package com.playtikaAcademy.w40kAddon.service.addon;

import com.playtikaAcademy.w40kAddon.addon.games.Card;
import com.playtikaAcademy.w40kAddon.addon.games.TwentyOne;
import com.playtikaAcademy.w40kAddon.entities.Warrior;
import com.playtikaAcademy.w40kAddon.exceptions.InaccessibleRequestException;
import com.playtikaAcademy.w40kAddon.repository.WarriorRepository;
import com.playtikaAcademy.w40kAddon.service.MainGameService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.playtikaAcademy.w40kAddon.addon.games.TwentyOne.OPPONENT;
import static com.playtikaAcademy.w40kAddon.addon.games.TwentyOne.PLAYER;

/**
 * 15.10.2019 19:19
 *
 * @author Edward
 */
@Data
public class TwentyOneGameService {

    private List<Card> playerCards;
    private List<Card> opponentsCards;
    private Map<String, Integer> warriorsAttempts = new HashMap<>();
    private static final int ACCESS_LEVEL = 3;
    private static final int ATTEMPTS_TO_PLAY = 3;
    private int countOfBonuses = 0;

    @Autowired
    private TwentyOne twentyOne;

    @Autowired
    WarriorRepository warriorRepository;

    @Autowired
    MainGameService mainGameService;

    public String startNewGame(String warriorName) {
        Warrior warrior = mainGameService.getWarriorByName(warriorName);
        if (warrior.getLevel() < ACCESS_LEVEL)
            throw new InaccessibleRequestException("You cannot play card game until warrior reach level number" + ACCESS_LEVEL + "!");
        storeAndCheckAttemptNumbersToAccessToStartNewGame(warriorName);
        twentyOne.newDeck();
        playerCards = twentyOne.drawTwoCard(PLAYER);
        opponentsCards = twentyOne.drawTwoCard(OPPONENT);
        return getResultAfterStartNewGame();
    }

    public Warrior acceptWarriorBonuses(String warriorName) {
        if (getCountOfBonuses() > 0) {
            Warrior warrior = mainGameService.getWarriorByName(warriorName);
            Warrior updatedWarrior = warrior.withBalance(warrior.getBalance() + 21000);
            setCountOfBonuses(getCountOfBonuses() - 1);
            return warriorRepository.save(updatedWarrior);
        } else throw new InaccessibleRequestException("Player do not win any bonuses");
    }

    public String singlePlayerGameStep(int choice) {
        if (playerCards.size() < 2 || twentyOne.isGameEnded(playerCards) || twentyOne.isGameEnded(opponentsCards)) {
            throw new InaccessibleRequestException("First of all new game must be started and both players must draw two cards!");
        }
        String resultAfterPlayerTurn = twentyOne.playerTurn(playerCards, choice);
        Integer playerScore = twentyOne.getGameScore().get(PLAYER);

        if (twentyOne.isGameEnded(playerCards)) {
            if (twentyOne.ifTwentyOne(playerCards)) {
                setCountOfBonuses(getCountOfBonuses() + 1);
            }
            return resultAfterPlayerTurn + "\n" + twentyOne.checkDeckAndGetResult(playerCards, PLAYER);
        }

        if (resultAfterPlayerTurn.equals("stand")) {
            String resultAfterOpponentTurn = twentyOne.opponentsTurn(opponentsCards);
            Integer opponentScore = twentyOne.getGameScore().get(OPPONENT);
            if (twentyOne.isGameEnded(opponentsCards)) {
                if (!twentyOne.ifTwentyOne(opponentsCards)) {
                    setCountOfBonuses(getCountOfBonuses() + 1);
                    return twentyOne.checkDeckAndGetResult(opponentsCards, OPPONENT) + ". So you win! Press ACCEPT to collect you bonuses!";
                }
                return "Your final score is: " + playerScore + "\n" + twentyOne.checkDeckAndGetResult(opponentsCards, OPPONENT);
            } else
                return resultAfterPlayerTurn + " score: " + playerScore + "\n" + resultAfterOpponentTurn + " score: " + opponentScore;
        }
        return resultAfterPlayerTurn + "Your score: " + playerScore + "\nSo press 1 to draw another card. Or press 2 to stand";
    }

    private String getResultAfterStartNewGame() {
        String result = "";
        if (twentyOne.isGameEnded(getPlayerCards())) {
            result = twentyOne.checkDeckAndGetResult(getPlayerCards(), PLAYER);
        }
        if (twentyOne.isGameEnded(getOpponentsCards())) {
            result = twentyOne.checkDeckAndGetResult(getOpponentsCards(), OPPONENT);
        }
        if (twentyOne.ifTwentyOne(getPlayerCards())) {
            setCountOfBonuses(getCountOfBonuses() + 1);
        }
        return StringUtils.isEmpty(result) ? "Your cards is: " + getPlayerCards() + " and score is: "
                + twentyOne.getGameScore().get(PLAYER) +
                "\nSo press 1 to draw another card. Or press 2 to stand" : result;
    }

    private void storeAndCheckAttemptNumbersToAccessToStartNewGame(String warriorName) {
        if (getWarriorsAttempts().get(warriorName) == null) {
            storeCurrentWarriorAttempts(warriorName);
            return;
        }
        if (getWarriorsAttempts().get(warriorName) != null && getWarriorsAttempts().get(warriorName) != ATTEMPTS_TO_PLAY) {
            storeCurrentWarriorAttempts(warriorName);
        } else
            throw new InaccessibleRequestException("You have only " + ATTEMPTS_TO_PLAY + " attempt to play twenty one game");
    }

    private void storeCurrentWarriorAttempts(String warriorName) {
        Map<String, Integer> warriorsAttempts = getWarriorsAttempts();
        if (warriorsAttempts.containsKey(warriorName)) {
            warriorsAttempts.replace(warriorName, warriorsAttempts.get(warriorName) + 1);
        } else {
            warriorsAttempts.put(warriorName, 1);
        }
    }
}
