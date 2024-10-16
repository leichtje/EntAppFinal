package com.teamfive.disscard.service;

import com.teamfive.disscard.dto.Card;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardServiceStub implements ICardService {
    @Override
    public Card getById(int id) {
        if (id == 9) {
            Card card = new Card();
            card.setId(9);
            card.setCardName("Charizard");
            card.setPopularity(8000);
            card.setMarketAvg("$3,600");
            return card;
        } else {
            return null;
        }
    }

    @Override
    public List<Card> getAll() {
        return List.of();
    }

    @Override
    public List<Card> searchByName(String keyword) {
        return List.of();
    }

    @Override
    public void save(Card card) {

    }
}
