package com.teamfive.disscard.dao;

import com.teamfive.disscard.dto.Card;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, Integer> {
}
