package com.tradingcards.model;

import jakarta.persistence.*;

@Entity
@Table(name = "deck_cards")
public class DeckCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deck_card_id")
    private Integer deckCardId;
    
    @Column(name = "deck_id", nullable = false)
    private Integer deckId;
    
    @Column(name = "card_id", nullable = false)
    private Integer cardId;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity = 1;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", insertable = false, updatable = false)
    private Card card;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id", insertable = false, updatable = false)
    private Deck deck;
    
    // Constructors
    public DeckCard() {}
    
    public DeckCard(Integer deckId, Integer cardId, Integer quantity) {
        this.deckId = deckId;
        this.cardId = cardId;
        this.quantity = quantity;
    }
    
    // Getters and Setters
    public Integer getDeckCardId() { return deckCardId; }
    public void setDeckCardId(Integer deckCardId) { this.deckCardId = deckCardId; }
    
    public Integer getDeckId() { return deckId; }
    public void setDeckId(Integer deckId) { this.deckId = deckId; }
    
    public Integer getCardId() { return cardId; }
    public void setCardId(Integer cardId) { this.cardId = cardId; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public Card getCard() { return card; }
    public void setCard(Card card) { this.card = card; }
    
    public Deck getDeck() { return deck; }
    public void setDeck(Deck deck) { this.deck = deck; }
}