package com.teamfive.disscard.helper;

import com.teamfive.disscard.dto.Card;

public class TestingUtils {

    /**
     * Generates a new Charizard card for testing
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

    /**
     * Generates a new Blastoise card for testing
     */
    public static Card generateBlastoiseCard() {

        Card card = new Card();

        card.setId(6);
        card.setCardName("Blastoise");
        card.setSeries("Pokemon");
        card.setFavoritesNum(800);
        card.setMarketAvg("$2,000");
        card.setPopularity(12000);

        return card;
    }
}
