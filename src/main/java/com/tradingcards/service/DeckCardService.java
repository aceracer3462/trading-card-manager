package com.tradingcards.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradingcards.model.DeckCard;
import com.tradingcards.repository.DeckCardRepository;

@Service
public class DeckCardService {
    
    @Autowired
    private DeckCardRepository deckCardRepository;
    
    public Integer getTotalCardQuantityInDeck(Integer deckId) {
        List<DeckCard> deckCards = deckCardRepository.findByDeckId(deckId);
        int total = 0;
        for (DeckCard dc : deckCards) {
            total += dc.getQuantity() != null ? dc.getQuantity() : 1;
        }
        return total;
    }
    
    // You can also add a @Query method in your repository
}