package com.teamfive.disscard.dao;

import com.teamfive.disscard.dto.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardDAOStub implements ICardDAO {

    // Represents all the cards stored in the database
    Map<Integer, Card> cardDataStore = new HashMap<>();

    @Override
    public Card save(Card card) throws Exception {

        int cardId = card.getId();

        if (cardDataStore.containsKey(cardId)) {
            throw new Exception("Card already exists.");
        } else {
            cardDataStore.put(cardId, card);
            return card;
        }
    }

    @Override
    public List<Card> getAll() {
        return new ArrayList<>(cardDataStore.values());
    }

    @Override
    public Card findById(int id) {
        return cardDataStore.get(id);
    }

    @Override
    public List<Card> getByName(String keyword) {

        ArrayList<Card> foundCards = new ArrayList<>();

        cardDataStore.forEach((id, card) -> {
            if (card.getCardName().contains(keyword)) {
                foundCards.add(card);
            }
        });

        return foundCards;
    }

    @Override
    public void delete(int id) {
        cardDataStore.remove(id);
    }
}
