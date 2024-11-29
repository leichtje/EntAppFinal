package com.teamfive.disscard.dao;

import com.teamfive.disscard.dto.PokemonApiCard;
import com.teamfive.disscard.dto.PokemonApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

@Repository
public class PokemonApiDAO implements IPokemonApiDAO {
    private static final Logger logger = LoggerFactory.getLogger(PokemonApiDAO.class);
    private final PokemonRetrofitClientInstance retrofitClientInstance;
    private final String API_KEY;

    @Autowired
    public PokemonApiDAO(
        PokemonRetrofitClientInstance retrofitClientInstance,
        @Value("${pokemon-api.api-key}") String apiKey
    ) {
        this.retrofitClientInstance = retrofitClientInstance;
        this.API_KEY = apiKey;
    }

    @Override
    public List<PokemonApiCard> searchCards(String q, int page, int pageSize, String orderBy, String select) throws IOException {
        Retrofit retrofitInstance = retrofitClientInstance.getRetrofitInstance();
        IPokemonRetrofitDAO pokemonRetrofitDAO = retrofitInstance.create(IPokemonRetrofitDAO.class);
        Call<PokemonApiResponse<List<PokemonApiCard>>> cardListCall = pokemonRetrofitDAO.searchCards(
                API_KEY,
                q,
                page,
                pageSize,
                orderBy,
                select
        );
        Response<PokemonApiResponse<List<PokemonApiCard>>> cardListResponse = cardListCall.execute();
        PokemonApiResponse<List<PokemonApiCard>> responseBody = cardListResponse.body();
        if (responseBody == null) {
            logger.error("Pokemon API Search returned null");
            return null;
        }
        return responseBody.getData();
    }

    @Override
    public PokemonApiCard getCard(String id, String select) throws IOException {
        Retrofit retrofitInstance = retrofitClientInstance.getRetrofitInstance();
        IPokemonRetrofitDAO pokemonRetrofitDAO = retrofitInstance.create(IPokemonRetrofitDAO.class);
        Call<PokemonApiResponse<PokemonApiCard>> cardCall = pokemonRetrofitDAO.getCard(API_KEY, id, select);
        Response<PokemonApiResponse<PokemonApiCard>> cardResponse = cardCall.execute();
        PokemonApiResponse<PokemonApiCard> responseBody = cardResponse.body();
        if (responseBody == null) {
            logger.error("Card does not exist");
            return null;
        }
        return responseBody.getData();
    }
}
