package com.teamfive.disscard.service;

import com.teamfive.disscard.dao.PokemonApiDAO;
import com.teamfive.disscard.dto.PokemonApiCard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokemonApiService implements IPokemonApiService {
    private static final Logger logger = LoggerFactory.getLogger(PokemonApiService.class);
    private PokemonApiDAO pokemonApiDAO;

    @Autowired
    public PokemonApiService(PokemonApiDAO pokemonApiDAO) {
        this.pokemonApiDAO = pokemonApiDAO;
    }

    @Override
    public List<PokemonApiCard> searchCardsByName(String name) throws Exception {
        return pokemonApiDAO.searchCards(
            "name:\"" + name + "\"",
            1,
            255,
            "number",
            null
        );
    }

    @Override
    public PokemonApiCard getCardById(String id) throws Exception {
        return getNonNullCard(id, null);
    }

    @Override
    public String getSmallImageUrlById(String id) throws Exception {
        PokemonApiCard card = getNonNullCard(id, "images");
        PokemonApiCard.ImageHash cardImages = getNonNullImageHash(card);
        return cardImages.getSmall();
    }

    @Override
    public String getLargeImageUrlById(String id) throws Exception {
        PokemonApiCard card = getNonNullCard(id, "images");
        PokemonApiCard.ImageHash cardImages = getNonNullImageHash(card);
        return cardImages.getLarge();
    }

    // Helper Methods

    private PokemonApiCard getNonNullCard(String id, String select) throws Exception {
        PokemonApiCard card = pokemonApiDAO.getCard(id, select);
        if (card == null) {
            logger.error("Pokemon TCG Card does not exist");
            throw new Exception("Pokemon TCG Card does not exist");
        }
        return card;
    }

    private PokemonApiCard.ImageHash getNonNullImageHash(PokemonApiCard card) throws Exception {
        PokemonApiCard.ImageHash cardImages = card.getImages();
        if (cardImages == null) {
            logger.error("Pokemon TCG Card does not have any images");
            throw new Exception("Pokemon TCG Card does not have any images");
        }
        return cardImages;
    }
}
