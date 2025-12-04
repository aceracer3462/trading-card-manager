package com.tradingcards.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradingcards.model.Deck;
import com.tradingcards.model.DeckCard;
import com.tradingcards.repository.DeckCardRepository;
import com.tradingcards.repository.DeckRepository;

@Service
public class DeckService {
    
    @Autowired
    private DeckRepository deckRepository;
    
    @Autowired
    private DeckCardRepository deckCardRepository;
    
    public List<Deck> getDecksWithCardCounts(Integer userId) {
        List<Deck> decks = deckRepository.findByUserId(userId);
        
        for (Deck deck : decks) {
            // Get all deck cards and sum their quantities
            List<DeckCard> deckCards = deckCardRepository.findByDeckId(deck.getDeckId());
            int totalQuantity = 0;
            for (DeckCard dc : deckCards) {
                totalQuantity += dc.getQuantity() != null ? dc.getQuantity() : 1;
            }
            deck.setCardCount(totalQuantity);
        }
        
        return decks;
    }
}