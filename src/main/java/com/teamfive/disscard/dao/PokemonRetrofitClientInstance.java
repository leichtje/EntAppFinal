package com.teamfive.disscard.dao;

import org.springframework.beans.factory.annotation.Value;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonRetrofitClientInstance {
    private static Retrofit retrofit;
    @Value("${pokemon-api.base-url}")
    private static String BASE_URL;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
