package com.teamfive.disscard.service;

import com.teamfive.disscard.dto.PokemonApiCard;

import java.util.List;

/**
 * Service object for accessing the Pokemon TCG API
 * @author Danny
 */
public interface IPokemonApiService {
    /**
     * Searches the API for cards that match the given name
     * @param name Name of the card being searched for
     * @return List of Pokemon card objects that match the given name
     */
    List<PokemonApiCard> searchCardsByName(String name) throws Exception;

    /**
     * Retrieves the card with the given ID from the API
     * @param id ID of the given card object in the Pokemon TCG API
     * @return Pokemon card object with the given ID
     */
    PokemonApiCard getCardById(String id) throws Exception;

    /**
     * Retrieves the url to the small image of the Pokemon card object with the given ID in the Pokemon TCG API
     * @param id ID of the given card object in the Pokemon TCG API
     * @return String containing the URL to the card's small image
     */
    String getSmallImageUrlById(String id) throws Exception;

    /**
     * Retrieves the url to the large image of the Pokemon card object with the given ID in the Pokemon TCG API
     * @param id ID of the given card object in the Pokemon TCG API
     * @return String containing the URL to the card's large image
     */
    String getLargeImageUrlById(String id) throws Exception;
}
