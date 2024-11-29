package com.teamfive.disscard.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class that generates and stores a Retrofit Client Instance for the Pokemon TCG API
 * that can be used elsewhere in the app
 * @author Danny
 */
@Component
public class PokemonRetrofitClientInstance {
    private static Logger logger = LoggerFactory.getLogger(PokemonRetrofitClientInstance.class);
    private static Retrofit retrofit;
    private final String BASE_URL;

    private PokemonRetrofitClientInstance(@Value("${pokemon-api.base-url}") String baseUrl) {
        this.BASE_URL = baseUrl;
    }

    /**
     * Returns the generated Retrofit Client Instance for the Pokemon TCG API
     * <div>
     *     If no instance exists, a new one will be generated.
     * </div>
     * @return Retrofit Client Instance
     */
    public Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            logger.info("Generating new Retrofit Client Instance");
            logger.debug("PokemonRetrofitClientInstance.BASE_URL: {}", BASE_URL);
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
