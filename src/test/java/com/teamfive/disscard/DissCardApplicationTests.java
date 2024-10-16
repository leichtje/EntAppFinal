package com.teamfive.disscard;

import com.teamfive.disscard.dto.Card;
import com.teamfive.disscard.service.ICardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class DissCardApplicationTests {

    @Autowired
    private ICardService cardService;
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
        assertEquals(8000, card.getPopularity());
        assertEquals("$3,600", card.getMarketAvg());
    }

    @Test
    void getCardById_returnsNotificationOfFailureFor0() {
        givenTradingCardCatalogAvailable();
        whenSearchCardWithId0();
        thenReturnId0IsInvalid();
    }

    private void whenSearchCardWithId0() {
        card = cardService.getById(0);
    }

    private void thenReturnId0IsInvalid() {
        assertNull(card);
    }

    @Test
    void saveCard_validateReturnWithBlastoiseAttributes() {
        givenAdminLoggedIn();
        whenBlastoiseCardDataUploadedWithValidFields();
        thenCreateBlastoiseCardAndReturnSuccessfulResponse();
    }

    private void givenAdminLoggedIn() {
    }

    private void whenBlastoiseCardDataUploadedWithValidFields() {
        card.setCardName("Blastoise");
        card.setPopularity(12000);
        card.setMarketAvg("$2,000");
    }

    private void thenCreateBlastoiseCardAndReturnSuccessfulResponse() {
        cardService.save(card);
    }

}
