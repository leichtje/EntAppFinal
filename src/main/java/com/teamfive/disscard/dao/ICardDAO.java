package com.teamfive.disscard.dao;

import com.teamfive.disscard.dto.Card;

import java.util.List;
import java.util.Optional;

public interface ICardDAO {
    Card save(Card card) throws Exception;

    List<Card> getAll();

    Card get(int id);

    List<Card> getByName(String keyword);

    void delete(int id);
}
