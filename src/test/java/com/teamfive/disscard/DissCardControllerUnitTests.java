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
 * @author Danny Murray
 */
@SpringBootTest
public class DissCardControllerUnitTests {

    private Card card;
    private static final Logger logger = LoggerFactory.getLogger(DissCardControllerUnitTests.class);
    private final DissCardController controller;

    @MockBean
    private ICardService cardService;

    @Autowired
    public DissCardControllerUnitTests(DissCardController controller) {
        this.controller = controller;
    }

    // ===== Frequently-used methods =====

    /**
     * Initialize controller and set up basic card service mock
     */
    private void givenCardServiceIsAvailable() {
        logger.info("Initializing card service mock");
        // Set controller's card service to card service mock
        controller.cardService = cardService;
    }

    /**
     * Generate a new card
     */
    private void generateCard() {
        logger.info("Generating card");
        card = new Card();
        card.setId(9);
        card.setCardName("Charizard");
        card.setSeries("Pokemon");
        card.setFavoritesNum(10000);
        card.setMarketAvg("$3,600");
        card.setPopularity(8000);
    }

    /**
     * Generate one card and save it to the card service mock
     */
    private void whenOneCardSavedToSystem() {
        // Generate Card
        generateCard();

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

    private void thenReturnOneCard(Card fetchedCard) {
        logger.info("Checking that the data of the returned card is accurate");
        assertEquals(fetchedCard, card);
    }

    // ===== Tests =====

    /**
     * Tests the fetchAllCards function of the controller
     */
    @Test
    public void fetchAllCards_returnsListOfCards() {
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
     */
    @Test
    public void fetchCardById_returnsOneCard() {
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

    /**
     * Tests fetchCardsByKeyword method of controller
     */
    @Test
    public void fetchCardsByKeyword_returnsListOfCards() {
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

    /**
     * Tests the addCard method of the controller
     */
    @Test
    public void addCard_returnsSavedCard() {
        givenCardServiceIsAvailable();
        Card returnedCard = whenAddCardToSystem();
        thenReturnOneCard(returnedCard);
    }

    private Card whenAddCardToSystem() {
        // Generate Card
        generateCard();

        // Return card when saving card
        logger.info("Setting service mock to return generated card when saving generated card");
        try {
            Mockito.when(cardService.save(card)).thenReturn(card);
        }
        catch (Exception e) {
            logger.error("Unable to set card service mock to save generated card");
            logger.error(e.getMessage());
        }

        // Add card through controller
        logger.info("Adding card to system");
        ResponseEntity<Card> response = controller.addCard(card);
        return response.getBody();
    }
}
