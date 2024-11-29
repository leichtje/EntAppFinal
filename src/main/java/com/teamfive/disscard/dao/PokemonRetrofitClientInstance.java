package com.teamfive.disscard.dao;

import org.springframework.beans.factory.annotation.Value;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class that generates and stores a Retrofit Client Instance for the Pokemon TCG API
 * that can be used elsewhere in the app
 */
public class PokemonRetrofitClientInstance {
    private static Retrofit retrofit;
    @Value("${pokemon-api.base-url}")
    private static String BASE_URL;

    /**
     * Returns the generated Retrofit Client Instance for the Pokemon TCG API
     * <div>
     *     If no instance exists, a new one will be generated.
     * </div>
     * @return Retrofit Client Instance
     */
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
