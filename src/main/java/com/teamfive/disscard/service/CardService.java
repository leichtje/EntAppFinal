package com.teamfive.disscard.service;

import com.teamfive.disscard.dao.ICardDAO;
import com.teamfive.disscard.dto.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService implements ICardService {

    private ICardDAO cardDAO;

    public CardService() {}

    @Autowired
    public CardService(ICardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }

    @Override
    public Card getById(int id) {

        Card foundCard = cardDAO.findById(id);

        // ID of card can't be zero or lower
        if (foundCard.getId() <= 0) {
            return null;
        } else {
            return foundCard;
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
        return cardDAO.save(card);
    }

}
