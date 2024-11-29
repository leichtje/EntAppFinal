package com.teamfive.disscard.dao;

import com.teamfive.disscard.dto.PokemonApiCard;
import org.springframework.beans.factory.annotation.Value;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * DAO Interface to be used by the Pokemon API Retrofit Client Instance
 */
public interface IPokemonRetrofitDAO {
    @Value("${pokemon-api.base-url}")
    String BASE_URL = "";
    @Value("${pokemon-api.api-key}")
    String API_KEY = "";

    /**
     * Retrieves a list of cards from the Pokemon TCG API that is filtered based
     * on the given parameters.
     * <div>
     *     For more detailed documentation of the format of the parameters, please visit
     *     <a href="https://docs.pokemontcg.io/api-reference/cards/search-cards">the official documentation</a>
     * </div>
     * @return List of card objects retrieved from the Pokemon TCG API, matching the given filters
     */
    @Headers({"X-Api-Key: " + API_KEY})
    @GET(BASE_URL + "cards/")
    Call<List<PokemonApiCard>> searchCards(
        @Query("q") String q,
        @Query("page") int page,
        @Query("pageSize") int pageSize,
        @Query("orderBy") String orderBy,
        @Query("select") String select
    );

    /**
     * Retrieves a single card from the Pokemon TCG API based on the given ID
     * <div>
     *     For more detailed documentation of the format of the parameters, please visit
     *     <a href="https://docs.pokemontcg.io/api-reference/cards/get-card">the official documentation</a>
     * </div>
     * @return The Pokemon card object with the given ID
     */
    @Headers({"X-Api-Key: " + API_KEY})
    @GET(BASE_URL + "cards/{id}")
    Call<PokemonApiCard> getCard(
        @Path("id") String id,
        @Query("select") String select
    );
}
