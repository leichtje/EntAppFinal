package com.teamfive.disscard.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import com.google.gson.annotations.*;

/**
 * DTO object representing card data
 * @author Danny Murray
 */
@Entity
public @Data class Card {

    /**
     * ID of card in database
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SerializedName("id")
    private int id;

    /**
     * ID of card in the Pokemon ID, if available
     */
    @SerializedName("pokemonApiId")
    private String pokemonApiId;

    /**
     * Name of card
     */
    @SerializedName("cardName")
    private String cardName;

    /**
     * String representing the series the card is from
     */
    @SerializedName("series")
    private String series;

    /**
     * Number measuring how many users have marked this card as a favorite
     */
    @SerializedName("favoritesNum")
    private int favoritesNum;

    /**
     * String representing the average price of this card on the market
     */
    @SerializedName("marketAvg")
    private String marketAvg;

    /**
     * Number representing the number of users that own the card
     */
    @SerializedName("popularity")
    private int popularity;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(cardName).append(" (").append(series).append(")");
        return sb.toString();
    }
}
