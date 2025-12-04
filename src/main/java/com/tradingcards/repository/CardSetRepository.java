package com.tradingcards.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tradingcards.model.CardSet;

@Repository
public interface CardSetRepository extends JpaRepository<CardSet, Integer> {
    
    Optional<CardSet> findBySetName(String setName);
    
    List<CardSet> findBySetNameContainingIgnoreCase(String setName);
    
    @Query("SELECT cs FROM CardSet cs ORDER BY cs.releaseDate DESC")
    List<CardSet> findAllOrderByReleaseDateDesc();
    
    @Query("SELECT cs FROM CardSet cs WHERE cs.releaseDate IS NOT NULL ORDER BY cs.releaseDate ASC")
    List<CardSet> findAllOrderByReleaseDateAsc();
    
    // Count cards in a set
    @Query("SELECT COUNT(c) FROM Card c WHERE c.cardSet.setId = :setId")
    Long countCardsInSet(@Param("setId") Integer setId);
    
    // Get total value of a set
    @Query("SELECT SUM(c.marketPrice) FROM Card c WHERE c.cardSet.setId = :setId AND c.marketPrice IS NOT NULL")
    BigDecimal getTotalSetValue(@Param("setId") Integer setId);
}