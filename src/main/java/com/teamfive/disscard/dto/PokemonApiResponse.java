package com.teamfive.disscard.dto;

import lombok.Data;

public @Data class PokemonApiResponse<DataType> {
    private DataType data;
}
