package com.teamfive.disscard;

import com.teamfive.disscard.dao.ICardDAO;
import com.teamfive.disscard.dto.Card;
import com.teamfive.disscard.helper.TestingUtils;
import com.teamfive.disscard.service.CardServiceStub;
import com.teamfive.disscard.service.ICardService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CardServiceTests {

    @Autowired
    private ICardService cardService;
    private Card card = new Card();
    private List<Card> cards = new ArrayList<>();

    @MockBean
    private ICardDAO cardDAO;

    private static final Logger logger = LoggerFactory.getLogger(CardServiceTests.class);

    @Test
    void getCardById_returnsCharizardForId9() throws Exception {
        logger.info("Setting up trading card catalog availability");
        givenTradingCardCatalogAvailable();
        
        logger.info("Searching card with ID 9");
        whenSearchCardWithId9();
        
        logger.info("Asserting that the returned card data matches Charizard's attributes");
        thenReturnCharizardCardDataForId9();
    }
    
    private void givenTradingCardCatalogAvailable() throws Exception {
        logger.info("Mocking cardDAO save method to return the card");
        when(cardDAO.save(card)).thenReturn(card);
        cardService = new CardServiceStub(cardDAO);
    }
    
    private void whenSearchCardWithId9() {
        logger.info("Fetching card by ID 9");
        card = cardService.getById(9);
    }
    
    private void thenReturnCharizardCardDataForId9() {
        logger.info("Checking if the returned card is Charizard");
        assertEquals("Charizard", card.getCardName());
        assertEquals("Pokemon", card.getSeries());
        assertEquals(10000, card.getFavoritesNum());
        assertEquals("$3,600", card.getMarketAvg());
        assertEquals(8000, card.getPopularity());
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
    void searchCardByName_returnListContainingCharizardFromKeywordChari() throws Exception {
        givenTradingCardCatalogAvailable();
        whenSearchCardByNameUsingKeywordChari();
        thenReturnListContainingCharizard();
    }

    private void whenSearchCardByNameUsingKeywordChari() {
        cards = cardService.searchByName("Chari");
        card = TestingUtils.generateCharizardCard();
    }

    private void thenReturnListContainingCharizard() {
        assertTrue(cards.contains(card));
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
        card.setSeries("Pokemon");
        card.setFavoritesNum(800);
        card.setMarketAvg("$2,000");
        card.setPopularity(12000);
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
        card.setSeries("Pokemon");
        card.setFavoritesNum(800);
        card.setMarketAvg("$2,000");
        card.setPopularity(12000);
    }

    private void thenReturnBlankNameIsInvalid() throws Exception {
        assertNotEquals(card, cardService.save(card));
        verify(cardDAO, atLeastOnce()).save(card);
    }
}
