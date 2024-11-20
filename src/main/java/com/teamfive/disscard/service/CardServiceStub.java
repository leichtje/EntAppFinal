package com.teamfive.disscard.service;

import com.teamfive.disscard.dao.ICardDAO;
import com.teamfive.disscard.dto.Card;
import com.teamfive.disscard.helper.TestingUtils;
import org.springframework.stereotype.Component;

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
            return TestingUtils.generateCharizardCard();
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
            Card card = TestingUtils.generateCharizardCard();
    
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

    private boolean isValidCard(Card card) {
        return card != null && !card.getCardName().isBlank();
    }
    
}
