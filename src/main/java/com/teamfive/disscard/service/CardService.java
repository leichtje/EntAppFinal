package com.teamfive.disscard.service;

import com.teamfive.disscard.dao.ICardDAO;
import com.teamfive.disscard.dto.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService implements ICardService {

    private ICardDAO cardDAO;
    private static final Logger logger = LoggerFactory.getLogger(CardService.class);

    public CardService() {}

    @Autowired
    public CardService(ICardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }

    @Override
    public Card getById(int id) {

        // ID of card can't be zero or lower
        if (id <= 0) {
            return null;
        } else {
            Card foundCard = cardDAO.findById(id);

            if (foundCard.getId() <= 0) {
                return null;
            } else {
                return foundCard;
            }
        }

    }

    @Override
    public List<Card> getAll() {
        return cardDAO.getAll();
    }

    @Override
    public List<Card> searchByName(String keyword) {
        return cardDAO.getByName(keyword);
    }

    @Override
    public Card save(Card card) throws Exception {
        if (isValidCard(card)) {
            return cardDAO.save(card);
        } else {
            throw new Exception("Card could not be saved, one of its properties is invalid.");
        }
    }

    /**
     * Used to determine if a card and it's values are valid.
     * @param card The card to check for validity.
     * @return True if the card is valid, false if not.
     */
    private boolean isValidCard(Card card) {

        boolean isValid = true;

        if (card == null) {
            isValid = false;
        } else if (card.getCardName().isBlank()) {
            isValid = false;
        } else if (card.getPopularity() < 0) {
            isValid = false;
        } else if (card.getFavoritesNum() < 0) {
            isValid = false;
        }

        return isValid;
    }

}
