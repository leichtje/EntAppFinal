package com.teamfive.disscard.dto;

import lombok.Data;
import com.google.gson.annotations.SerializedName;

/**
 * DTO object representing card data
 */
public @Data class Card {
    /**
     * ID of card in database
     */
    @SerializedName("id")
    public int id;
    /**
     * Name of card
     * <div>
     *     TODO: add @SerializedName annotation once database is set up to map column names to attribute names
     * </div>
     */
    public String cardName;
    /**
     * String representing the series the card is from
     * <div>
     *     TODO: add @SerializedName annotation once database is set up to map column names to attribute names
     * </div>
     */
    public String series;
    /**
     * Number measuring how many users have marked this card as a favorite
     * <div>
     *     TODO: add @SerializedName annotation once database is set up to map column names to attribute names
     * </div>
     */
    public int favoritesNum;
    /**
     * String representing the average price of this card on the market
     * <div>
     *     TODO: add @SerializedName annotation once database is set up to map column names to attribute names
     * </div>
     */
    public String marketAvg;
    /**
     * Number representing the number of users that own the card
     * <div>
     *     TODO: add @SerializedName annotation once database is set up to map column names to attribute names
     * </div>
     */
    public int popularity;

    public String ToString() {
        return cardName + " (" + series + ")";
    }
}
