package com.teamfive.disscard;

import com.teamfive.disscard.dto.Card;
import com.teamfive.disscard.service.ICardService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Class adding unit tests for the DissCardController class
 */
@SpringBootTest
public class DissCardControllerUnitTests {

    private Card card;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final DissCardController controller;

    @MockBean
    private ICardService cardService;

    @Autowired
    public DissCardControllerUnitTests(DissCardController dissCardController) {
        controller = dissCardController;
    }

    // ===== Frequently-used methods =====

    /**
     * Initialize controller and set up basic card service mock
     * @throws Exception Could not initialize card service mock
     */
    private void givenCardServiceIsAvailable() throws Exception {
        logger.info("Initializing card service mock");
        // Set up mock of cardService
        Mockito.when(cardService.save(card)).thenReturn(card);
        // Initialize controller
        controller.cardService = cardService;
    }

    /**
     * Generate one card and save it to the card service mock
     */
    private void whenOneCardSavedToSystem() {
        // Generate Card
        logger.info("Generating card");
        card = new Card();
        card.setId(9);
        card.setCardName("Charizard");
        card.setSeries("Pokemon");
        card.setFavoritesNum(10000);
        card.setMarketAvg("$3,600");
        card.setPopularity(8000);

        // Return card when getting card by ID
        logger.info("Setting service mock to return generated card when getting by id");
        Mockito.when(cardService.getById(card.getId())).thenReturn(card);

        // Return list containing card when getting all cards
        logger.info("Setting service mock to return list containing generated card when getting all cards");
        List<Card> cardList = new ArrayList<>();
        cardList.add(card);
        Mockito.when(cardService.getAll()).thenReturn(cardList);

        // Return list containing card when searching by keyword
        logger.info("Setting service mock to return list containing generated card when searching by keyword");
        Mockito.when(cardService.searchByName(card.getCardName())).thenReturn(cardList);
    }

    private void thenReturnListContainingOneCard(List<Card> cardList) {
        // Check that list contains 1 card
        logger.info("Checking that card list only contains one card");
        assertEquals(cardList.size(), 1);

        // Check values of card in list
        logger.info("Checking that the data of the card in the list is accurate");
        Card cardInList = cardList.getFirst();
        assertEquals(cardInList, card);
    }

    // ===== Tests =====

    /**
     * Tests the fetchAllCards function of the controller
     * @throws Exception Could not initialize card service mock
     */
    @Test
    public void fetchAllCards_returnsListOfCards() throws Exception {
        givenCardServiceIsAvailable();
        whenOneCardSavedToSystem();
        List<Card> cardList = whenFetchAllCards();
        thenReturnListContainingOneCard(cardList);
    }

    private List<Card> whenFetchAllCards() {
        logger.info("Fetching list of all cards");
        return controller.fetchAllCards();
    }

    /**
     * Tests the fetchCardById method of the controller
     * @throws Exception Could not initialize card service mock
     */
    @Test
    public void fetchCardById_returnsOneCard() throws Exception {
        givenCardServiceIsAvailable();
        whenOneCardSavedToSystem();
        Card fetchedCard = whenFetchingCardById();
        thenReturnOneCard(fetchedCard);
    }

    private Card whenFetchingCardById() {
        logger.info("Fetching card by id");
        ResponseEntity<Card> response = controller.fetchCardById(card.getId());
        return response.getBody();
    }

    private void thenReturnOneCard(Card fetchedCard) {
        logger.info("Checking that the data of the returned card is accurate");
        assertEquals(fetchedCard, card);
    }

    /**
     * Tests fetchCardsByKeyword method of controller
     * @throws Exception Could not initialize card service mock
     */
    @Test
    public void fetchCardsByKeyword_returnsListOfCards() throws Exception {
        givenCardServiceIsAvailable();
        whenOneCardSavedToSystem();
        List<Card> cardList = whenFetchingCardsByKeyword();
        thenReturnListContainingOneCard(cardList);
    }

    private List<Card> whenFetchingCardsByKeyword() {
        logger.info("Fetching list of cards by keyword");
        ResponseEntity<List<Card>> response = controller.fetchCardsByKeyword(card.getCardName());
        return response.getBody();
    }
}
