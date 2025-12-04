package com.tradingcards.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tradingcards.model.DeckCard;

@Repository
public interface DeckCardRepository extends JpaRepository<DeckCard, Integer> {
    
    List<DeckCard> findByDeckId(Integer deckId);
    
    DeckCard findByDeckIdAndCardId(Integer deckId, Integer cardId);
    
    // Count unique cards in a deck (number of rows)
    long countByDeckId(Integer deckId);
    
    // NEW: Sum total quantity of cards in a deck
    @Query("SELECT SUM(dc.quantity) FROM DeckCard dc WHERE dc.deckId = :deckId")
    Integer sumQuantityByDeckId(@Param("deckId") Integer deckId);
    
    void deleteByDeckId(Integer deckId);
}