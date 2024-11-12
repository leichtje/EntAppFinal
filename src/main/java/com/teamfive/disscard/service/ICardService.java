package com.teamfive.disscard.service;

import com.teamfive.disscard.dto.Card;

import java.util.List;

public interface ICardService {
    /**
     * Gets the card with the specified ID.
     * @param id The id of the card.
     * @return The card that matches the ID, or null if nothing is found
     */
    Card getById(int id);

    /**
     * Gets every card that exists.
     * @return A list containing every card recorded.
     */
    List<Card> getAll();

    /**
     * Gets cards based on if the keyword matches their name.
     * @param keyword The keyword you want to use for the search.
     * @return A list containing any card that has a name matching or partially matching the given keyword.
     */
    List<Card> searchByName(String keyword);

    /**
     * Saves the given card info in storage.
     * @param card The card you wish to record.
     * @return The card that was saved.
     */
    Card save(Card card) throws Exception;

    /**
     * Deletes the card from storage.
     * @param card The card you wish to delete.
     * @return The card that was deleted.
    */
    Card delete(Card card) throws Exception;
}
