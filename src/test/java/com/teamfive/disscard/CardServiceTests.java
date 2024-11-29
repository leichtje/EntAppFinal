package com.teamfive.disscard;

import com.teamfive.disscard.dao.ICardDAO;
import com.teamfive.disscard.dto.Card;
import com.teamfive.disscard.helper.TestingUtils;
import com.teamfive.disscard.service.CardService;
import com.teamfive.disscard.service.ICardService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CardServiceTests {

    private ICardService cardService;
    private Card card;
    // Used to compare card submitted to be saved and the card returned after saving.
    Card savedCard;
    private List<Card> cards = new ArrayList<>();

    @Mock
    private ICardDAO cardDAO;

    private static final Logger logger = LoggerFactory.getLogger(CardServiceTests.class);

    @Test
    void getCardById_returnsCharizardForId9() {
        logger.info("Setting up trading card catalog availability");
        givenTradingCardCatalogAvailable();
        
        logger.info("Searching card with ID 9");
        whenSearchCardWithId9();
        
        logger.info("Asserting that the returned card data matches Charizard's attributes");
        thenReturnCharizardCardDataForId9();
    }
    
    private void givenTradingCardCatalogAvailable() {
        logger.info("Mocking cardDAO save method to return the card");
        cardService = new CardService(cardDAO);
    }
    
    private void whenSearchCardWithId9() {
        logger.info("Fetching card by ID 9");
        when(cardDAO.findById(9)).thenReturn(TestingUtils.generateCharizardCard());
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
    void getCardById_returnsNotificationOfFailureFor0() {
        givenTradingCardCatalogAvailable();
        whenSearchCardWithId0();
        thenReturnId0IsInvalid();
    }

    private void whenSearchCardWithId0() {
        when(cardDAO.findById(0)).thenReturn(null);
        card = cardService.getById(0);
    }

    private void thenReturnId0IsInvalid() {
        assertNull(card);
    }

    @Test
    void searchCardByName_returnListContainingCharizardFromKeywordChari() {
        givenTradingCardCatalogAvailable();
        whenSearchCardByNameUsingKeywordChari();
        thenReturnListContainingCharizard();
    }

    private void whenSearchCardByNameUsingKeywordChari() {
        // Is what will be returned by card service
        ArrayList<Card> searchResults = new ArrayList<>();
        searchResults.add(TestingUtils.generateCharizardCard());

        // Search results should be every name that contains the characters Chari
        when(cardDAO.getByName("Chari")).thenReturn(searchResults);
        cards = cardService.searchByName("Chari");
    }

    private void thenReturnListContainingCharizard() {
        // Making sure each card contains Chari as part of their name
        for (Card card : cards) {
            String cardName = card.getCardName();
            assertTrue(cardName.contains("Chari"));
        }
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

    private void whenBlastoiseCardDataUploadedWithValidFields() throws Exception {
        card = TestingUtils.generateBlastoiseCard();
        when(cardDAO.save(card)).thenReturn(card);
        savedCard = cardService.save(card);
        verify(cardDAO, atLeastOnce()).save(card);
    }

    private void thenCreateBlastoiseCardAndReturnSuccessfulResponse() {
        assertEquals(card.getCardName(), savedCard.getCardName());
        assertEquals(card.getSeries(), savedCard.getSeries());
        assertEquals(card.getFavoritesNum(), savedCard.getFavoritesNum());
        assertEquals(card.getMarketAvg(), savedCard.getMarketAvg());
        assertEquals(card.getPopularity(), savedCard.getPopularity());
    }

    @Test
    void saveCard_returnsNotificationOfFailureForEmptyName() throws Exception {
        givenAdminLoggedIn();
        givenTradingCardCatalogAvailable();
        whenCardDataUploadedWithEmptyName();
        thenReturnBlankNameIsInvalid();
    }

    private void whenCardDataUploadedWithEmptyName() throws Exception {
        card = TestingUtils.generateBlastoiseCard();
        card.setCardName("");
        when(cardDAO.save(card)).thenThrow(Exception.class);
    }

    private void thenReturnBlankNameIsInvalid() {
        assertThrows(Exception.class, () -> cardService.save(card));
    }
}
