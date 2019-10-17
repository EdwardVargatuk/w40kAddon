package com.playtikaAcademy.w40kAddon.addon.games;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 16.10.2019 8:33
 *
 * @author Edward
 */
@Data
@AllArgsConstructor
public
class Card {

    @Getter
    @AllArgsConstructor
    public enum Rank {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6),
        SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(2), QUEEN(3), KING(4), ACE(11);

        private final int numVal;
    }

    public enum Suit {CLUBS, DIAMONDS, HEARTS, SPADES}

    private final Rank rank;
    private final Suit suit;

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
