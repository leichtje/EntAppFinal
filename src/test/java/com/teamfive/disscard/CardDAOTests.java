package com.teamfive.disscard;

import com.teamfive.disscard.dao.CardDAO;
import com.teamfive.disscard.dao.CardRepository;
import com.teamfive.disscard.dao.ICardDAO;
import com.teamfive.disscard.dto.Card;
import com.teamfive.disscard.helper.TestingUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CardDAOTests {

    ICardDAO cardDAO;
    Card card;
    // Used to compare card submitted to be saved and the card returned after saving.
    Card savedCard;

    @Mock
    CardRepository cardRepository;

    @Test
    void receiveId9_returnCharizardCard() {
        givenCardRepositoryAvailable();
        whenCard9AddedIsCharizard();
        whenFindCardById9();
        thenReturnCharizardCard();
    }

    private void givenCardRepositoryAvailable() {
        cardDAO = new CardDAO(cardRepository);
    }

    private void whenCard9AddedIsCharizard() {
        Card charizard = TestingUtils.generateCharizardCard();
        Mockito.when(cardRepository.findById(9)).thenReturn(Optional.of(charizard));
    }

    private void whenFindCardById9() {
        card = cardDAO.findById(9);
    }

    private void thenReturnCharizardCard() {
        assertEquals("Charizard", card.getCardName());
        assertEquals("Pokemon", card.getSeries());
        assertEquals(10000, card.getFavoritesNum());
        assertEquals("$3,600", card.getMarketAvg());
        assertEquals(8000, card.getPopularity());
    }

    @Test
    void receiveNonExistentId_returnId0Card() {
        givenCardRepositoryAvailable();
        whenFindCardByNonExistentId();
        thenReturnCardWithId0();
    }

    private void whenFindCardByNonExistentId() {
        card = cardDAO.findById(-1);
    }

    private void thenReturnCardWithId0() {
        assertEquals(0, card.getId());
    }

    @Test
    void saveCard_returnSavedBlastoiseCard() throws Exception {
        givenCardRepositoryAvailable();
        whenBlastoiseCardSaved();
        thenReturnSavedBlastoiseCard();
    }

    private void whenBlastoiseCardSaved() throws Exception {
        card = TestingUtils.generateBlastoiseCard();
        when(cardRepository.save(card)).thenReturn(card);
        savedCard = cardDAO.save(card);
        verify(cardRepository, atLeastOnce()).save(card);
    }

    private void thenReturnSavedBlastoiseCard() {
        assertEquals(card.getCardName(), savedCard.getCardName());
        assertEquals(card.getSeries(), savedCard.getSeries());
        assertEquals(card.getFavoritesNum(), savedCard.getFavoritesNum());
        assertEquals(card.getMarketAvg(), savedCard.getMarketAvg());
        assertEquals(card.getPopularity(), savedCard.getPopularity());
    }
}
