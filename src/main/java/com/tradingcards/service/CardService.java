package com.tradingcards.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tradingcards.model.Card;
import com.tradingcards.repository.CardRepository;

@Service
public class CardService {
    
    @Autowired
    private CardRepository cardRepository;
    
    // Existing methods (keep these)
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }
    
    public Page<Card> getAllCardsPaginated(Pageable pageable) {
        return cardRepository.findAll(pageable);
    }
    
    public Card getCardById(Integer id) {
        return cardRepository.findById(id).orElse(null);
    }
    
    public Page<Card> advancedSearchPaginated(String keyword, Pageable pageable) {
        return cardRepository.advancedSearch(keyword, pageable);
    }
    
    public Page<Card> filterByType(String cardType, Pageable pageable) {
        return cardRepository.findByCardType(cardType, pageable);
    }
    
    public Page<Card> filterByRarity(String rarity, Pageable pageable) {
        return cardRepository.findByRarity(rarity, pageable);
    }
    
    public Page<Card> filterByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return cardRepository.findByPriceRange(minPrice, maxPrice, pageable);
    }
    
    public List<String> getAllCardTypes() {
        return cardRepository.findDistinctCardTypes();
    }
    
    public List<String> getAllRarities() {
        return cardRepository.findDistinctRarities();
    }
    
    public Long getCardCount() {
        return cardRepository.countAllCards();
    }
    
    public BigDecimal getHighestCardPrice() {
        return cardRepository.getMaxPrice();
    }
    
    public Double getAverageCardPrice() {
        return cardRepository.getAveragePrice();
    }
    
    // NEW METHODS FOR GAME FILTERING:
    
    public Page<Card> filterByGame(String game, Pageable pageable) {
        return cardRepository.findByGame(game, pageable);
    }
    
    public Page<Card> searchWithGame(String keyword, String game, Pageable pageable) {
        return cardRepository.searchWithGame(keyword, game, pageable);
    }
    
    public List<String> getAllGames() {
        return cardRepository.findDistinctGames();
    }
}