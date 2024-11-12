package com.teamfive.disscard.service;

import com.teamfive.disscard.dao.ICardDAO;
import com.teamfive.disscard.dto.Card;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService implements ICardService {

    private ICardDAO cardDAO;

    public CardService() {}

    public CardService(ICardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }

    @Override
    public Card getById(int id) {
        return cardDAO.findById(id);
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
