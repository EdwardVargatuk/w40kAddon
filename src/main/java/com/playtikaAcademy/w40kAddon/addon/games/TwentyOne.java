package com.playtikaAcademy.w40kAddon.addon.games;

import com.playtikaAcademy.w40kAddon.exceptions.DeckEmptyException;
import lombok.Data;

import java.util.*;
import java.util.stream.IntStream;

/**
 * 16.10.2019 0:15
 *
 * @author Edward
 */
@Data
public class TwentyOne {

    public static final String PLAYER = "Player";
    public static final String OPPONENT = "Opponent";
    private List<Card> protoDeck;
    private Map<String, Integer> gameScore;

    public TwentyOne() {
        newDeck();
        gameScore = new HashMap<>();
        gameScore.put(PLAYER, 0);
        gameScore.put(OPPONENT, 0);
    }

    public void newDeck() {
        initProtoDeck();
        Collections.shuffle(protoDeck);
    }

    /**
     * always calls only at the start
     */
    public List<Card> drawTwoCard(String gamester) {
        List<Card> cards = new ArrayList<>();
        IntStream.range(0, 2).forEach(value -> cards.add(drawCard()));
        updateScore(cards, gamester);
        return cards;
    }

    public String playerTurn(List<Card> cards, int choice) {
        if (choice == 1) {
            return addNewCardToDeckAndUpdateScore(cards, PLAYER);
        } else if (choice == 2) {
            return "stand";
        } else {
            return "Please select 1 to draw card or 2 to stand";
        }
    }

    public String opponentsTurn(List<Card> cards) {
        if (protoDeck.size() > 0) {
            return addNewCardToDeckAndUpdateScore(cards, OPPONENT);
        } else throw new DeckEmptyException("Deck is empty");
    }

    public boolean isGameEnded(List<Card> cardList) {
        return calcTotalScore(cardList) >= 21;
    }

    public boolean ifTwentyOne(List<Card> cards) {
        return (calcTotalScore(cards) == 21);
    }

    public String checkDeckAndGetResult(List<Card> cards, String gamester) {
        if (isGameEnded(cards)) {
            if (ifTwentyOne(cards)) {
                return gamester.equals(PLAYER)
                        ? "Congratulations, you got 21!\nPress ACCEPT to collect you bonuses!"
                        : "Opponent got 21";
            } else return gamester + " loose! and got: " + calcTotalScore(cards);
        }
        return "Cards of " + gamester + " is: " + cards + " that gives score of:" + getGameScore().get(gamester);
    }

    private void initProtoDeck() {
        protoDeck = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values())
            for (Card.Rank rank : Card.Rank.values()) {
                this.protoDeck.add(new Card(rank, suit));
            }
    }

    private Card drawCard() {
        if (getProtoDeck().size() > 0) {
            Card card = (protoDeck).get(0);
            protoDeck.remove(0);
            return card;
        } else throw new DeckEmptyException("Deck is empty");
    }

    private String addNewCardToDeckAndUpdateScore(List<Card> cards, String opponent) {
        Card beforeRemoteCard = protoDeck.get(0);
        Card newCard = drawCard();
        cards.add(newCard);
        updateScore(cards, opponent);
        return opponent + " drew a " + beforeRemoteCard + ".";
    }

    private void updateScore(List<Card> cards, String gamester) {
        getGameScore().replace(gamester, calcTotalScore(cards));
    }

    private int calcTotalScore(List<Card> cards) {
        int total = 0;
        for (Card card : cards) {
            total += card.getRank().getNumVal();
        }
        return total;
    }
}
