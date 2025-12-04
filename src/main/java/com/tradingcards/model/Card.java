package com.tradingcards.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cards")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Card {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Integer cardId;
    
    @Column(name = "card_name", nullable = false, length = 200)
    private String cardName;
    
    @Column(name = "card_type", length = 50)
    private String cardType;
    
    @Column(name = "mana_cost", length = 20)
    private String manaCost;
    
    @Column(name = "rarity", length = 20)
    private String rarity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "set_id", referencedColumnName = "set_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CardSet cardSet;
    
    @Column(name = "market_price", precision = 12, scale = 2)
    private BigDecimal marketPrice;
    
    @Column(name = "text_box", columnDefinition = "TEXT")
    private String textBox;
    
    // ADD THIS: Image URL field
    @Column(name = "image_url", length = 500)
    private String imageUrl;

    // ADD THIS: Game field
    @Column(name = "game", length = 50)
    private String game;
    
    // Constructors
    public Card() {}
    
    // Update constructor to include imageUrl
    public Card(String cardName, String cardType, String game, String manaCost, String rarity, 
                CardSet cardSet, BigDecimal marketPrice, String textBox, String imageUrl) {
        this.cardName = cardName;
        this.cardType = cardType;
        this.game = game;
        this.manaCost = manaCost;
        this.rarity = rarity;
        this.cardSet = cardSet;
        this.marketPrice = marketPrice;
        this.textBox = textBox;
        this.imageUrl = imageUrl;
    }
    
    // Getters and Setters
    public Integer getCardId() { return cardId; }
    public void setCardId(Integer cardId) { this.cardId = cardId; }
    
    public String getCardName() { return cardName; }
    public void setCardName(String cardName) { this.cardName = cardName; }
    
    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }
    
    public String getManaCost() { return manaCost; }
    public void setManaCost(String manaCost) { this.manaCost = manaCost; }
    
    public String getRarity() { return rarity; }
    public void setRarity(String rarity) { this.rarity = rarity; }
    
    public CardSet getCardSet() { return cardSet; }
    public void setCardSet(CardSet cardSet) { this.cardSet = cardSet; }
    
    public BigDecimal getMarketPrice() { return marketPrice; }
    public void setMarketPrice(BigDecimal marketPrice) { this.marketPrice = marketPrice; }
    
    public String getTextBox() { return textBox; }
    public void setTextBox(String textBox) { this.textBox = textBox; }
    
    // ADD getter and setter for imageUrl
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public Integer getSetId() {
        return cardSet != null ? cardSet.getSetId() : null;
    }

    // ADD getter and setter for game
    public String getGame() { return game; }
    public void setGame(String game) { this.game = game; }
    
    @Override
    public String toString() {
        return cardName + " (" + cardType + ")";
    }
}