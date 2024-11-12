package com.teamfive.disscard;

import com.teamfive.disscard.dto.Card;
import com.teamfive.disscard.service.ICardService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DissCardControllerUnitTests {
    private DissCardController controller;
    private Card card;

    @MockBean
    private ICardService cardService;

    // ===== Heavily-used methods =====

    private void givenCardServiceIsAvailable() throws Exception {
        // Set up mock of cardService
        Mockito.when(cardService.save(card)).thenReturn(card);
        // Initialize controller
        controller = new DissCardController();
        controller.cardService = cardService;
    }

    private void whenOneCardSavedToSystem() {
        // Generate Card
        card = new Card();
        card.setId(9);
        card.setCardName("Charizard");
        card.setSeries("Pokemon");
        card.setFavoritesNum(10000);
        card.setMarketAvg("$3,600");
        card.setPopularity(8000);

        // Return card when getting card by ID
        Mockito.when(cardService.getById(9)).thenReturn(card);

        // Return list containing card when getting all cards
        List<Card> cardList = new ArrayList<>();
        cardList.add(card);
        Mockito.when(cardService.getAll()).thenReturn(cardList);
    }

    // ===== Tests =====

    @Test
    public void fetchAllCards_returnsListOfCards() throws Exception {
        // Given card service and controller are initialized
        givenCardServiceIsAvailable();
        // When one card is saved to system
        whenOneCardSavedToSystem();
        // When fetching list of all cards
        List<Card> cardList = whenFetchAllCards();
        // Then return list of cards containing one card.
        thenReturnListContainingOneCard(cardList);
    }

    private List<Card> whenFetchAllCards() {
        return controller.fetchAllCards();
    }

    private void thenReturnListContainingOneCard(List<Card> cardList) {
        // Check that list contains 1 card
        assertEquals(cardList.size(), 1);

        // Check values of card in list
        Card cardInList = cardList.getFirst();
        assertEquals(cardInList, card);
    }
}
