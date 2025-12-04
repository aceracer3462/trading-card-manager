package com.tradingcards.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tradingcards.model.Deck;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Integer> {
    
    // Find decks by user ID
    List<Deck> findByUserId(Integer userId);
}