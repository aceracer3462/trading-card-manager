package com.tradingcards.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tradingcards.model.Card;
import com.tradingcards.model.CardSet;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    
    // Add these methods:
    
    // Find by game
    Page<Card> findByGame(String game, Pageable pageable);
    
    // Search with game filter
    @Query("SELECT c FROM Card c WHERE " +
           "(LOWER(c.cardName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.cardType) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.rarity) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.manaCost) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND c.game = :game")
    Page<Card> searchWithGame(@Param("keyword") String keyword, 
                             @Param("game") String game, 
                             Pageable pageable);
    
    // Get distinct games
    @Query("SELECT DISTINCT c.game FROM Card c WHERE c.game IS NOT NULL ORDER BY c.game")
    List<String> findDistinctGames();
    
    // Basic CRUD methods
    List<Card> findByCardNameContainingIgnoreCase(String name);
    Page<Card> findByCardNameContainingIgnoreCase(String name, Pageable pageable);
    
    List<Card> findByCardType(String cardType);
    Page<Card> findByCardType(String cardType, Pageable pageable);
    
    List<Card> findByRarity(String rarity);
    Page<Card> findByRarity(String rarity, Pageable pageable);
    
    // UPDATED: Find by CardSet relationship
    List<Card> findByCardSet(CardSet cardSet);
    Page<Card> findByCardSet(CardSet cardSet, Pageable pageable);

    List<Card> findByGame(String game);
    
    // UPDATED: Find by Set ID through CardSet
    @Query("SELECT c FROM Card c WHERE c.cardSet.setId = :setId")
    List<Card> findBySetId(@Param("setId") Integer setId);
    
    @Query("SELECT c FROM Card c WHERE c.cardSet.setId = :setId")
    Page<Card> findBySetId(@Param("setId") Integer setId, Pageable pageable);
    
    // Find by set name
    @Query("SELECT c FROM Card c WHERE LOWER(c.cardSet.setName) LIKE LOWER(CONCAT('%', :setName, '%'))")
    List<Card> findBySetNameContainingIgnoreCase(@Param("setName") String setName);
    
    // Sorting
    List<Card> findAllByOrderByCardNameAsc();
    List<Card> findAllByOrderByMarketPriceDesc();
    
    // Pagination support
    Page<Card> findAll(Pageable pageable);
    
    // Advanced search
    @Query("SELECT c FROM Card c WHERE " +
           "LOWER(c.cardName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.cardType) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.rarity) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.manaCost) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Card> advancedSearch(@Param("keyword") String keyword, Pageable pageable);
    
    // Filter by price range
    @Query("SELECT c FROM Card c WHERE c.marketPrice BETWEEN :minPrice AND :maxPrice")
    Page<Card> findByPriceRange(@Param("minPrice") BigDecimal minPrice, 
                                @Param("maxPrice") BigDecimal maxPrice, 
                                Pageable pageable);
    
    // Complex search with joins
    @Query("SELECT c FROM Card c WHERE " +
       "(:name IS NULL OR LOWER(c.cardName) LIKE LOWER(CONCAT('%', CAST(:name AS string), '%'))) AND " +
       "(:type IS NULL OR c.cardType = :type) AND " +
       "(:game IS NULL OR c.game = :game) AND " +  
       "(:rarity IS NULL OR c.rarity = :rarity) AND " +
       "(:minPrice IS NULL OR c.marketPrice >= :minPrice) AND " +
       "(:maxPrice IS NULL OR c.marketPrice <= :maxPrice) AND " +
       "(:setId IS NULL OR c.cardSet.setId = :setId)")
    List<Card> searchCards(@Param("name") String name,
                          @Param("type") String type,
                          @Param("game") String game,  
                          @Param("rarity") String rarity,
                          @Param("minPrice") BigDecimal minPrice,
                          @Param("maxPrice") BigDecimal maxPrice,
                          @Param("setId") Integer setId);
    
    // Get cards with highest value
    @Query("SELECT c FROM Card c WHERE c.marketPrice IS NOT NULL ORDER BY c.marketPrice DESC")
    Page<Card> findMostExpensiveCards(Pageable pageable);
    
    // Get cards with lowest value
    @Query("SELECT c FROM Card c WHERE c.marketPrice IS NOT NULL ORDER BY c.marketPrice ASC")
    Page<Card> findCheapestCards(Pageable pageable);
    
    // Get distinct card types for filters
    @Query("SELECT DISTINCT c.cardType FROM Card c WHERE c.cardType IS NOT NULL ORDER BY c.cardType")
    List<String> findDistinctCardTypes();
    
    // Get distinct rarities for filters
    @Query("SELECT DISTINCT c.rarity FROM Card c WHERE c.rarity IS NOT NULL ORDER BY c.rarity")
    List<String> findDistinctRarities();
    
    // Get card statistics
    @Query("SELECT COUNT(c) FROM Card c")
    Long countAllCards();
    
    @Query("SELECT AVG(c.marketPrice) FROM Card c WHERE c.marketPrice IS NOT NULL")
    Double getAveragePrice();
    
    @Query("SELECT MAX(c.marketPrice) FROM Card c")
    BigDecimal getMaxPrice();
    
    @Query("SELECT MIN(c.marketPrice) FROM Card c WHERE c.marketPrice IS NOT NULL")
    BigDecimal getMinPrice();
}