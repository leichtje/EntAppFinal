package com.teamfive.disscard.service;

import com.teamfive.disscard.dto.Card;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardServiceStub implements ICardService {
    @Override
    public Card getById(int id) {
        Card card = new Card();
        card.setId(9);
        card.setCardName("Charizard");
        card.setPopularity("5%");
        card.setMarketAvg("$3,600");
        return card;
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
