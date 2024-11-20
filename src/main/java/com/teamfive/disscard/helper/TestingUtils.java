package com.teamfive.disscard.helper;

import com.teamfive.disscard.dto.Card;

public class TestingUtils {

    /**
     * Generates a new Charizard card
     */
    public static Card generateCharizardCard() {

        Card card = new Card();

        card.setId(9);
        card.setCardName("Charizard");
        card.setSeries("Pokemon");
        card.setFavoritesNum(10000);
        card.setMarketAvg("$3,600");
        card.setPopularity(8000);

        return card;
    }
}
