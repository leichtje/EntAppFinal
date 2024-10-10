package com.teamfive.disscard;

import com.teamfive.disscard.dto.Card;
import com.teamfive.disscard.service.ICardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DissCardApplicationTests {

    @Autowired
    ICardService cardService;
    private Card card = new Card();

    @Test
    void contextLoads() {
    }

    @Test
    void getCardById_returnsCharizardForId9() {
        givenTradingCardCatalogAvailable();
        whenSearchCardWithId9();
        thenReturnCharizardCardDataForId9();
    }

    private void givenTradingCardCatalogAvailable() {
    }

    private void whenSearchCardWithId9() {
        card = cardService.getById(9);
    }

    private void thenReturnCharizardCardDataForId9() {
        assertEquals("Charizard", card.getCardName());
        assertEquals("5%", card.getPopularity());
        assertEquals("$3,600", card.marketAvg());
    }

}
