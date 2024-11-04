package com.teamfive.disscard.dao;

import com.teamfive.disscard.dto.Card;

import java.util.List;

public interface ICardDAO {
    Card save(Card card) throws Exception;

    List<Card> getAll();

    Card findById(int id);

    List<Card> getByName(String keyword);

    void delete(int id);
}
