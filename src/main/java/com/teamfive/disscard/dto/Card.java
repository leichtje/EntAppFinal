package com.teamfive.disscard.dto;

import lombok.Data;
import com.google.gson.annotations.*;

/**
 * DTO object representing card data
 * @author Danny Murray
 */
public @Data class Card {
    /**
     * ID of card in database
     */
    @SerializedName("id")
    public int id;
    /**
     * Name of card
     */
    @SerializedName("cardName")
    public String cardName;
    /**
     * String representing the series the card is from
     */
    @SerializedName("series")
    public String series;
    /**
     * Number measuring how many users have marked this card as a favorite
     */
    @SerializedName("favoritesNum")
    public int favoritesNum;
    /**
     * String representing the average price of this card on the market
     */
    @SerializedName("marketAvg")
    public String marketAvg;
    /**
     * Number representing the number of users that own the card
     */
    @SerializedName("popularity")
    public int popularity;

    public String ToString() {
        return cardName + " (" + series + ")";
    }
}
