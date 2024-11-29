package com.teamfive.disscard.service;

import com.teamfive.disscard.dao.ICardDAO;
import com.teamfive.disscard.dto.Card;
import com.teamfive.disscard.helper.TestingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CardServiceStub implements ICardService {

    private ICardDAO cardDAO;
    private static final Logger logger = LoggerFactory.getLogger(CardServiceStub.class);

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

            logger.error("Error saving card: {}", e.getMessage());
          
            return null;
        }
    }

    @Override
    public void delete(int id) {
        cardDAO.delete(id);
    }

    /**
     * Used to determine if a card and it's values are valid.
     * @param card The card to check for validity.
     * @return True if the card is valid, false if not.
     */
    private static boolean isValidCard(Card card) {
        return card != null && !card.getCardName().isBlank();
    }
    
}
