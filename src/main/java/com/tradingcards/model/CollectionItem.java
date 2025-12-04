package com.tradingcards.model;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "user_collection")
public class CollectionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collection_id")
    private Integer collectionId;
    
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    
    @Column(name = "card_id", nullable = false)
    private Integer cardId;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity = 1;
    
    @Column(name = "condition", length = 20)
    private String condition = "Mint";
    
    @Column(name = "condition_price", precision = 12, scale = 2)
    private BigDecimal conditionPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", insertable = false, updatable = false)
    private Card card;
    
    // Constructors
    public CollectionItem() {}
    
    public CollectionItem(Integer userId, Integer cardId, Integer quantity, String condition) {
        this.userId = userId;
        this.cardId = cardId;
        this.quantity = quantity;
        this.condition = condition;
    }
    
    // Getters and Setters
    public Integer getCollectionId() { return collectionId; }
    public void setCollectionId(Integer collectionId) { this.collectionId = collectionId; }
    
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    
    public Integer getCardId() { return cardId; }
    public void setCardId(Integer cardId) { this.cardId = cardId; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
    
    public Card getCard() { return card; }
    public void setCard(Card card) { this.card = card; }

    public BigDecimal getConditionPrice() { return conditionPrice; }
    public void setConditionPrice(BigDecimal conditionPrice) { this.conditionPrice = conditionPrice; }
}