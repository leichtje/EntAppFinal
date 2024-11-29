package com.teamfive.disscard.dao;

import com.teamfive.disscard.dto.PokemonApiCard;
import org.springframework.stereotype.Repository;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

@Repository
public class PokemonApiDAO implements IPokemonApiDAO {
    @Override
    public List<PokemonApiCard> searchCards(String q, int page, int pageSize, String orderBy, String select) throws IOException {
        Retrofit retrofitInstance = PokemonRetrofitClientInstance.getRetrofitInstance();
        IPokemonRetrofitDAO pokemonRetrofitDAO = retrofitInstance.create(IPokemonRetrofitDAO.class);
        Call<List<PokemonApiCard>> cardListCall = pokemonRetrofitDAO.searchCards(q, page, pageSize, orderBy, select);
        Response<List<PokemonApiCard>> cardListResponse = cardListCall.execute();
        return cardListResponse.body();
    }

    @Override
    public PokemonApiCard getCard(String id, String select) throws IOException {
        Retrofit retrofitInstance = PokemonRetrofitClientInstance.getRetrofitInstance();
        IPokemonRetrofitDAO pokemonRetrofitDAO = retrofitInstance.create(IPokemonRetrofitDAO.class);
        Call<PokemonApiCard> cardCall = pokemonRetrofitDAO.getCard(id, select);
        Response<PokemonApiCard> cardResponse = cardCall.execute();
        return cardResponse.body();
    }
}
