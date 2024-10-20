package com.teamfive.disscard;

import com.teamfive.disscard.dao.ICardDAO;
import com.teamfive.disscard.dto.Card;
import com.teamfive.disscard.service.CardServiceStub;
import com.teamfive.disscard.service.ICardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class DissCardApplicationTests {

    @Autowired
    private ICardService cardService;
    private Card card = new Card();

    @MockBean
    private ICardDAO cardDAO;

    @Test
    void contextLoads() {
    }

    @Test
    void getCardById_returnsCharizardForId9() throws Exception {
        givenTradingCardCatalogAvailable();
        whenSearchCardWithId9();
        thenReturnCharizardCardDataForId9();
    }

    private void givenTradingCardCatalogAvailable() throws Exception {
        when(cardDAO.save(card)).thenReturn(card);
        cardService = new CardServiceStub(cardDAO);
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
    void getCardById_returnsNotificationOfFailureFor0() throws Exception {
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
    void saveCard_validateReturnWithBlastoiseAttributes() throws Exception {
        givenAdminLoggedIn();
        givenTradingCardCatalogAvailable();
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

    private void thenCreateBlastoiseCardAndReturnSuccessfulResponse() throws Exception {
        assertEquals(card, cardService.save(card));
        verify(cardDAO, atLeastOnce()).save(card);
    }

    @Test
    void saveCard_returnsNotificationOfFailureForEmptyName() throws Exception {
        givenAdminLoggedIn();
        givenTradingCardCatalogAvailable();
        whenCardDataUploadedWithEmptyName();
        thenReturnBlankNameIsInvalid();
    }

    private void whenCardDataUploadedWithEmptyName() {
        card.setCardName("");
        card.setPopularity(12000);
        card.setMarketAvg("$2,000");
    }

    private void thenReturnBlankNameIsInvalid() throws Exception {
        assertNotEquals(card, cardService.save(card));
        verify(cardDAO, atLeastOnce()).save(card);
    }
}
