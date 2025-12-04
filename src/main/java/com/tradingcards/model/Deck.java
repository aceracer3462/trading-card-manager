package com.tradingcards.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient; 

@Entity
@Table(name = "decks")
public class Deck {
    
     // Add this field to your Deck class
    @Transient  // This won't be stored in the database
    private Integer cardCount;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deck_id")
    private Integer deckId;
    
    @Column(name = "deck_name", nullable = false, length = 100)
    private String deckName;
    
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    
    @Column(name = "date_created")
    private LocalDate dateCreated = LocalDate.now();
       
    // Constructors
    public Deck() {}
    
    public Deck(String deckName, Integer userId) {
        this.deckName = deckName;
        this.userId = userId;
    }
    
    // Getters and Setters
    public Integer getDeckId() { return deckId; }
    public void setDeckId(Integer deckId) { this.deckId = deckId; }
    
    public String getDeckName() { return deckName; }
    public void setDeckName(String deckName) { this.deckName = deckName; }
    
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    
    public LocalDate getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDate dateCreated) { this.dateCreated = dateCreated; }

    public Integer getCardCount() {
        return cardCount;
    }
    
    public void setCardCount(Integer cardCount) {
        this.cardCount = cardCount;
    }
}