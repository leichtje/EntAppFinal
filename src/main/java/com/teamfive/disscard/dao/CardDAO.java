package com.teamfive.disscard.dao;

import com.teamfive.disscard.dto.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CardDAO implements ICardDAO {

    private CardRepository cardRepository;
    private static final Logger logger = LoggerFactory.getLogger(CardDAO.class);

    public CardDAO() {}

    @Autowired
    public CardDAO(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Card save(Card card) throws Exception {
        return cardRepository.save(card);
    }

    @Override
    public List<Card> getAll() {

        ArrayList<Card> cards = new ArrayList<>();

        for (Card card : cardRepository.findAll()) {
            cards.add(card);
        }
        return cards;
    }

    @Override
    public Card findById(int id) {
        Optional<Card> card = cardRepository.findById(id);

        if (card.isPresent()) {
            return card.get();
        } else {
            logger.warn("Card with Id {} not found by card repository. Returning card with default values.", id);
            return new Card();
        }
    }

    @Override
    public List<Card> getByName(String keyword) {

        ArrayList<Card> cards = new ArrayList<>();

        for (Card card : cardRepository.findAll()) {

            String cardName = card.getCardName();

            if (cardName.contains(keyword)) {
                cards.add(card);
            }
        }
        return cards;
    }

    @Override
    public void delete(int id) {
        cardRepository.deleteById(id);
    }
}
