package com.teamfive.disscard.service;

import com.teamfive.disscard.dao.ICardDAO;
import com.teamfive.disscard.dto.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Component
public class CardServiceStub implements ICardService {

    private ICardDAO cardDAO;

    public CardServiceStub() {}

    public CardServiceStub(ICardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }

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
        return cardDAO.getAll();
    }

    @Override
    public List<Card> searchByName(String keyword) {
        if (keyword.equals("Chari")) {
            Card card = new Card();
            card.setId(9);
            card.setCardName("Charizard");
            card.setPopularity(8000);
            card.setMarketAvg("$3,600");

            List<Card> searchResults = new ArrayList<>();
            searchResults.add(card);
            return searchResults;
        } else {
            return null;
        }
    }

    @Override
    public Card save(Card card) throws Exception {
        Card savedCard = cardDAO.save(card);

        if (savedCard.getCardName() == null || savedCard.getCardName().isEmpty()) {
            return null;
        } else {
            return savedCard;
        }
    }
}
