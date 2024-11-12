package com.teamfive.disscard.service;

import com.teamfive.disscard.dao.ICardDAO;
import com.teamfive.disscard.dto.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CardServiceStub implements ICardService {

    private ICardDAO cardDAO;

    public CardServiceStub() {}

    public CardServiceStub(ICardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }

    private boolean isValidCard(Card card) {
        return card != null && card.getCardName() != null && !card.getCardName().isEmpty();
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
        List<Card> cards = cardDAO.getAll();
        return cards != null ? cards : new ArrayList<>();
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
            return new ArrayList<>();
        }
    }
    

    @Override
  public Card save(Card card) {
        try {
            Card savedCard = cardDAO.save(card);
    
            if (savedCard.getCardName() == null || savedCard.getCardName().isEmpty()) {
                return null;
            } else {
                return savedCard;
            }
        } catch (Exception e) {
            System.err.println("Error saving card: " + e.getMessage());
          

            return null;
        }
    }
    
}
