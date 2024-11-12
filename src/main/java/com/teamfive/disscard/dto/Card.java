package com.teamfive.disscard.dto;

import lombok.Data;
import com.google.gson.annotations.*;

import java.math.BigDecimal;

/**
 * DTO object representing card data
 * @author Danny Murray
 */
public @Data class Card {
    /**
     * ID of card in database
     */
    @SerializedName("id")
    private int id;
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
