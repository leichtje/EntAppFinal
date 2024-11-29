package com.teamfive.disscard.dao;

import com.teamfive.disscard.dto.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.teamfive.disscard.helper.TestingUtils.*;

public class CardDAOStub implements ICardDAO {

    // Represents all the cards stored in the database
    private Map<Integer, Card> cardDataStore = new HashMap<>();

    public CardDAOStub() {
        // Adds cards to fake database so stub can be used for testing
        Card charizard = generateCharizardCard();
        cardDataStore.put(charizard.getId(), charizard);
    }

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

        Card card = cardDataStore.get(id);

        if (card == null) {
            return new Card();
        } else {
            return cardDataStore.get(id);
        }

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
