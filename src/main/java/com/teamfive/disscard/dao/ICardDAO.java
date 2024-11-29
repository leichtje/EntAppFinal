package com.teamfive.disscard.dao;

import com.teamfive.disscard.dto.Card;

import java.util.List;

public interface ICardDAO {
    /**
     * Saves the card to the database.
     * @param card The card object.
     * @return The card that was saved.
     * @throws Exception If something goes wrong with the save operation.
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
     * @return The card that matches the ID, or a new Card with default values if nothing is found.
     */
    Card findById(int id);

    /**
     * Gets the list of cards whose name's contain the given keyword.
     * @param keyword The search term used.
     * @return The list of cards that contain the keyword. The list will be empty if nothing is found.
     */
    List<Card> getByName(String keyword);

    /**
     * Deletes the card with the specified ID from the database.
     * @param id The id of the card.
     */
    void delete(int id);
}
