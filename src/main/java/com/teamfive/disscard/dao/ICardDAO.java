package com.teamfive.disscard.dao;

import com.teamfive.disscard.dto.Card;

import java.util.List;

public interface ICardDAO {
    /**
     * Saves the card to the database.
     * @param card The card object.
     * @return The card that was saved.
     */
    Card save(Card card) throws Exception;

    /**
     * Gets every card that exists.
     * @return A list containing every card recorded.
     */
    List<Card> getAll();

    /**
     * Finds the card with the specified ID.
     * @param id The id of the card.
     * @return The card that matches the ID, or null if nothing is found.
     */
    Card findById(int id);

    /**
     * Gets the list of cards with the specified name.
     * @param keyword The name of the card.
     * @return The list of cards that match the keyword, or null if nothing is found.
     */
    List<Card> getByName(String keyword);

    /**
     * Deletes the card with the specified ID from the database.
     * @param id The id of the card.
     */
    void delete(int id);
}
