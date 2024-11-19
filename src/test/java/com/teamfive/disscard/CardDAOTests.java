package com.teamfive.disscard;

import com.teamfive.disscard.dao.CardDAOStub;
import com.teamfive.disscard.dto.Card;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CardDAOTests {

    CardDAOStub cardDAO = new CardDAOStub();
    Card card = new Card();

    @Test
    void receiveId9_returnCharizardCard() {
        givenCardRepositoryAvailable();
        whenFindCardById9();
        thenReturnCharizardCard();
    }

    private void givenCardRepositoryAvailable() {}

    private void whenFindCardById9() {}

    private void thenReturnCharizardCard() {}

    @Test
    void receiveNonExistentId_returnError() {
        givenCardRepositoryAvailable();
        whenFindCardByNonExistentId();
        thenReturnCardWithIdCouldNotbeFound();
    }

    private void whenFindCardByNonExistentId() {}

    private void thenReturnCardWithIdCouldNotbeFound() {}

}
