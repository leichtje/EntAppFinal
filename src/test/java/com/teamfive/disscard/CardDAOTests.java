package com.teamfive.disscard;

import com.teamfive.disscard.dao.CardDAOStub;
import com.teamfive.disscard.dao.CardRepository;
import com.teamfive.disscard.dao.ICardDAO;
import com.teamfive.disscard.dto.Card;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CardDAOTests {

    @Autowired
    ICardDAO cardDAO;
    Card card = new Card();

    @Mock
    CardRepository cardRepository;

    @Test
    void receiveId9_returnCharizardCard() throws Exception {
        givenCardRepositoryAvailable();
        whenFindCardById9();
        thenReturnCharizardCard();
    }

    private void givenCardRepositoryAvailable() throws Exception {
        when(cardRepository.save(card)).thenReturn(card);
        cardDAO = new CardDAOStub();
    }

    private void whenFindCardById9() throws Exception {
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
    void receiveNonExistentId_returnId0Card() throws Exception {
        givenCardRepositoryAvailable();
        whenFindCardByNonExistentId();
        thenReturnCardWithId0();
    }

    private void whenFindCardByNonExistentId() throws Exception {
        card = cardDAO.findById(-1);
    }

    private void thenReturnCardWithId0() {
        assertEquals(0, card.getId());
    }

}
