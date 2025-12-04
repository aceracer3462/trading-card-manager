package com.tradingcards.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tradingcards.repository.CardRepository;
import com.tradingcards.repository.CollectionRepository;
import com.tradingcards.repository.DeckRepository;
import com.tradingcards.repository.UserRepository;

@RestController
@RequestMapping("/api/database")
public class DatabaseController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CardRepository cardRepository;
    
    @Autowired
    private CollectionRepository collectionRepository;
    
    @Autowired
    private DeckRepository deckRepository;
    
    @GetMapping("/status")
    public Map<String, Object> getDatabaseStatus() {
        Map<String, Object> status = new HashMap<>();
        
        status.put("database", "PostgreSQL");
        status.put("users", userRepository.count());
        status.put("cards", cardRepository.count());
        status.put("collections", collectionRepository.count());
        status.put("decks", deckRepository.count());
        status.put("demoUserExists", userRepository.findByUsername("demo").isPresent());
        status.put("timestamp", java.time.LocalDateTime.now().toString());
        
        return status;
    }
}