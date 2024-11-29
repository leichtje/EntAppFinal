package com.teamfive.disscard.dao;

import com.teamfive.disscard.dto.PokemonApiCard;

import java.io.IOException;
import java.util.List;

/**
 * DAO for accessing the Pokemon TCG API
 * @author Danny
 */
public interface IPokemonApiDAO {
    /**
     * Retrieves a list of cards from the Pokemon TCG API that is filtered based
     * on the given parameters.
     * @param q The search query. Examples can be found in
     *     <a href="https://docs.pokemontcg.io/api-reference/cards/search-cards">the official documentation</a>
     * @param page The page of data to access.
     * @param pageSize The maximum amount of cards to return.
     * @param orderBy The field(s) to order the results by. Examples can be found in
     *     <a href="https://docs.pokemontcg.io/api-reference/cards/search-cards">the official documentation</a>
     * @param select A comma delimited list of fields to return in the response (ex. ?select=id,name). By default,
     *     all fields are returned if this query parameter is not used.
     * @return List of Pokemon TCG card objects retrieved from the Pokemon TCG API, matching the given filters
     */
    List<PokemonApiCard> searchCards(
        String q,
        int page,
        int pageSize,
        String orderBy,
        String select
    ) throws IOException;

    /**
     * Retrieves a Pokemon TCG card object from the Pokemon TCG API based on the given ID
     * @param id The Id of the Pokemon TCG card object
     * @param select A comma delimited list of fields to return in the response (ex. ?select=id,name). By default,
     *      all fields are returned if this query parameter is not used.
     * @return The Pokemon TCG card object with the given ID
     */
    PokemonApiCard getCard(String id, String select) throws IOException ;
}
