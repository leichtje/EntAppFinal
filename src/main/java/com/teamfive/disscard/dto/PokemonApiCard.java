package com.teamfive.disscard.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import java.util.List;

/**
 * DTO for card objects returned by the Pokemon TCG API we're using
 * <div>
 *     For more thorough documentation on the contents of this class, please visit
 *     <a href="https://docs.pokemontcg.io/api-reference/cards/card-object">the official documentation</a>
 * </div>
 * @author Danny
 */
public @Data class PokemonApiCard {

    // Data Fields

    private String id;
    private String name;
    private String supertype;
    private List<String> subtypes;
    private String level;
    private String hp;
    private List<String> types;
    private String evolvesFrom;
    private List<String> evolvesTo;
    private List<String> rules;
    private AncientTraitHash ancientTrait;
    private List<AbilityHash> abilities;
    private List<AttackHash> attacks;
    private List<TypeMatchupHash> weaknesses;
    private List<TypeMatchupHash> resistances;
    private List<String> retreatCost;
    private int convertedRetreatCost;
    private String number;
    private String artist;
    private String rarity;
    private String flavorText;
    private List<Integer> nationalPokedexNumbers;
    private LegalityHash legalities;
    private String regulationMark;
    private ImageHash images;
    private TcgPlayerHash tcgplayer;
    private CardMarketHash cardmarket;

    // Complex JSON Types
    public static @Data class AncientTraitHash {
        private String name;
        private String text;
    }
    public static @Data class AbilityHash {
        private String name;
        private String text;
        private String type;
    }
    public static @Data class AttackHash {
        private List<String> cost;
        private String name;
        private String text;
        private String damage;
        private int convertedEnergyCost;
    }
    public static @Data class TypeMatchupHash {
        private String type;
        private String value;
    }
    public static @Data class LegalityHash {
        private String standard;
        private String expanded;
        private String unlimited;
    }
    public static @Data class ImageHash {
        private String small;
        private String large;
    }
    public static @Data class TcgPlayerHash {
        private String url;
        private String updatedAt;
        private TCGP_PriceHash prices;
    }
    public static @Data class TCGP_PriceHash {
        private double low;
        private double mid;
        private double high;
        private double market;
        private double directLow;
    }
    public static @Data class CardMarketHash {
        private String url;
        private String updatedAt;
        private CM_PriceHash prices;
    }
    public static @Data class CM_PriceHash {
        private double averageSellPrice;
        private double lowPrice;
        private double trendPrice;
        private double germanProLow;
        private double suggestedPrice;
        private double reverseHoloSell;
        private double reverseHoloLow;
        private double reverseHoloTrend;
        private double lowPriceExPlus;
        private double avg1;
        private double avg7;
        private double avg30;
        private double reverseHoloAvg1;
        private double reverseHoloAvg7;
        private double reverseHoloAvg30;
    }
}
