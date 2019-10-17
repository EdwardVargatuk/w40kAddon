package com.playtikaAcademy.w40kAddon.addon.games;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 17.10.2019 15:13
 *
 * @author Edward
 */
class TwentyOneTest {

    private TwentyOne twentyOne;
    private List<Card> cards;

    @BeforeEach
    void setUp() {
        twentyOne = new TwentyOne();
        cards = new ArrayList<>();
    }

    @Test
    void shouldNewDeckMustContainsFiftyTwoCards() {
        int expectedNubOfCardsExceptOfJokers = 52;
        twentyOne.drawTwoCard(TwentyOne.PLAYER);
        twentyOne.drawTwoCard(TwentyOne.PLAYER);
        twentyOne.drawTwoCard(TwentyOne.OPPONENT);

        twentyOne.newDeck();
        List<Card> protoDeck = twentyOne.getProtoDeck();

        assertEquals(expectedNubOfCardsExceptOfJokers, protoDeck.size());
    }

    @Test
    void shouldAfterDrawTwoCardDeckSizeIsLessThanTwo() {
        List<Card> protoDeck = twentyOne.getProtoDeck();
        int expectedSizeAfterDrawTwoCard = protoDeck.size() - 2;

        twentyOne.drawTwoCard(TwentyOne.PLAYER);

        assertEquals(expectedSizeAfterDrawTwoCard, protoDeck.size());
    }

    @Test
    void shouldAfterDrawingTwoCardGameScoreIsUpdated() {
        Integer notUpdatedScore = twentyOne.getGameScore().get(TwentyOne.PLAYER);
        twentyOne.drawTwoCard(TwentyOne.PLAYER);
        Integer actualScore = twentyOne.getGameScore().get(TwentyOne.PLAYER);

        assertNotEquals(notUpdatedScore, actualScore);
    }

    @Test
    void shouldGameEndedIfTwentyOne() {
        cards.add(new Card(Card.Rank.TEN, Card.Suit.CLUBS));
        cards.add(new Card(Card.Rank.ACE, Card.Suit.CLUBS));

        assertTrue(twentyOne.isGameEnded(cards));
    }

    @Test
    void shouldGameEndedIfMoreThanTwentyOne() {
        cards.add(new Card(Card.Rank.ACE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Rank.ACE, Card.Suit.DIAMONDS));

        assertTrue(twentyOne.isGameEnded(cards));
    }

    @Test
    void shouldGameNotEndedIfLessThanTwentyOne() {
        cards.add(new Card(Card.Rank.ACE, Card.Suit.DIAMONDS));

        assertFalse(twentyOne.isGameEnded(cards));
    }


    @Test
    void shouldScoreIsTwentyOneBasedOnCardIntValue() {
        cards.add(new Card(Card.Rank.TEN, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Rank.ACE, Card.Suit.DIAMONDS));

        assertTrue(twentyOne.ifTwentyOne(cards));
    }

    @Test
    void shouldScoreIsNotTwentyIfLessOrMoreThanTwentyOne() {
        cards.add(new Card(Card.Rank.ACE, Card.Suit.DIAMONDS));

        assertFalse(twentyOne.ifTwentyOne(cards));

        cards.add(new Card(Card.Rank.TEN, Card.Suit.CLUBS));
        cards.add(new Card(Card.Rank.ACE, Card.Suit.DIAMONDS));

        assertFalse(twentyOne.ifTwentyOne(cards));
    }

    @Test
    void shouldAfterPlayerTurnInsertNumberReturnCorrectResultMessage() {
        List<Card> playerCards = twentyOne.drawTwoCard(TwentyOne.PLAYER);
        String expectedToChoice_Two = "stand";
        String expectedToChoice_Five = "Please select 1 to draw card or 2 to stand";

        String result_Two = twentyOne.playerTurn(playerCards, 2);
        String result_Five = twentyOne.playerTurn(playerCards, 5);

        assertEquals(expectedToChoice_Two, result_Two);
        assertEquals(expectedToChoice_Five, result_Five);
    }

    @Test
    void shouldAfterPlayerTurnPlayerCardsSizeIncreasesAndDeckSizeIsDecreases() {
        List<Card> playerCards = twentyOne.drawTwoCard(TwentyOne.PLAYER);
        List<Card> protoDeck = twentyOne.getProtoDeck();
        int protoDeckSizeBeforePlayerTurn = protoDeck.size();
        int playerCardsSizeBeforePlayerTurn = playerCards.size();
        twentyOne.playerTurn(playerCards, 1);

        int actualPlayerCardsSizeAfter = playerCards.size();
        int actualProtoDeckSizeAfter = protoDeck.size();

        assertEquals(protoDeckSizeBeforePlayerTurn - 1, actualProtoDeckSizeAfter);
        assertEquals(playerCardsSizeBeforePlayerTurn + 1, actualPlayerCardsSizeAfter);
    }

    @Test
    void shouldAfterOpponentTurnOpponentCardsSizeIncreasesAndDeckSizeIsDecreases() {
        List<Card> opponentCards = twentyOne.drawTwoCard(TwentyOne.OPPONENT);
        List<Card> protoDeck = twentyOne.getProtoDeck();
        int protoDeckSizeBeforeOpponentTurn = protoDeck.size();
        int opponentCardsSizeBeforeOpponentTurn = opponentCards.size();
        //twice
        twentyOne.opponentsTurn(opponentCards);
        twentyOne.opponentsTurn(opponentCards);

        int actualOpponentCardsSizeAfter = opponentCards.size();
        int actualProtoDeckSizeAfter = protoDeck.size();

        assertEquals(protoDeckSizeBeforeOpponentTurn - 2, actualProtoDeckSizeAfter);
        assertEquals(opponentCardsSizeBeforeOpponentTurn + 2, actualOpponentCardsSizeAfter);
    }
}