package com.tradingcards.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tradingcards.model.CollectionItem;  // ADD THIS IMPORT

@Repository
public interface CollectionRepository extends JpaRepository<CollectionItem, Integer> {
    List<CollectionItem> findByUserId(Integer userId);
    Page<CollectionItem> findByUserId(Integer userId, Pageable pageable);
    
    // Returns ALL matching items (not just one) - each condition treated separately
    List<CollectionItem> findByUserIdAndCardId(Integer userId, Integer cardId);
    
    // Find by userId and condition
    List<CollectionItem> findByUserIdAndCondition(Integer userId, String condition);
    
    // Find specific item by userId, cardId AND condition
    Optional<CollectionItem> findByUserIdAndCardIdAndCondition(Integer userId, Integer cardId, String condition);
    
    void deleteByUserIdAndCardId(Integer userId, Integer cardId);
    
    // Delete specific item by userId, cardId AND condition
    void deleteByUserIdAndCardIdAndCondition(Integer userId, Integer cardId, String condition);
    
    // Get collection statistics
    @Query("SELECT SUM(ci.quantity) FROM CollectionItem ci WHERE ci.userId = :userId")
    Integer getTotalCardCount(@Param("userId") Integer userId);
    
    @Query("SELECT COUNT(DISTINCT ci.cardId) FROM CollectionItem ci WHERE ci.userId = :userId")
    Integer getUniqueCardCount(@Param("userId") Integer userId);
    
    // Get total value with condition adjustment
    @Query("SELECT SUM(ci.conditionPrice * ci.quantity) FROM CollectionItem ci WHERE ci.userId = :userId AND ci.conditionPrice IS NOT NULL")
    BigDecimal getTotalCollectionValue(@Param("userId") Integer userId);
}