package com.tradingcards.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "condition_multipliers")
public class ConditionMultiplier {
    
    @Id
    @Column(name = "condition_name", length = 50)
    private String conditionName;
    
    @Column(name = "multiplier", precision = 5, scale = 2, nullable = false)
    private BigDecimal multiplier = BigDecimal.ONE;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    // Constructors
    public ConditionMultiplier() {}
    
    public ConditionMultiplier(String conditionName, BigDecimal multiplier, String description) {
        this.conditionName = conditionName;
        this.multiplier = multiplier;
        this.description = description;
    }
    
    // Getters and Setters
    public String getConditionName() { return conditionName; }
    public void setConditionName(String conditionName) { this.conditionName = conditionName; }
    
    public BigDecimal getMultiplier() { return multiplier; }
    public void setMultiplier(BigDecimal multiplier) { this.multiplier = multiplier; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}