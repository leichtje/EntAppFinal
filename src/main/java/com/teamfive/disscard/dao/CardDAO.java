package com.teamfive.disscard.dao;

import com.teamfive.disscard.dto.Card;

import java.util.ArrayList;
import java.util.List;

public class CardDAO implements ICardDAO {

    CardRepository cardRepository;

    @Override
    public Card save(Card card) throws Exception {
        return cardRepository.save(card);
    }

    @Override
    public List<Card> getAll() {

        ArrayList<Card> cards = new ArrayList<>();
        Iterable<Card> allCards = cardRepository.findAll();

        for (Card card : allCards) {
            cards.add(card);
        }
        return cards;
    }

    @Override
    public Card get(int id) {
        return cardRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Card> getByName(String keyword) {

        ArrayList<Card> cards = new ArrayList<>();
        Iterable<Card> allCards = cardRepository.findAll();

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
