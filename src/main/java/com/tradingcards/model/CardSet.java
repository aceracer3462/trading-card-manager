package com.tradingcards.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sets")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // ADD THIS
public class CardSet {
    // ... rest of your code
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "set_id")
    private Integer setId;
    
    @Column(name = "set_name", nullable = false, length = 100)
    private String setName;
    
    @Column(name = "release_date")
    private LocalDate releaseDate;
    
    @Column(name = "symbol", length = 50)
    private String symbol;
    
    // Constructors
    public CardSet() {}
    
    public CardSet(String setName, LocalDate releaseDate, String symbol) {
        this.setName = setName;
        this.releaseDate = releaseDate;
        this.symbol = symbol;
    }
    
    // Getters and Setters
    public Integer getSetId() { return setId; }
    public void setSetId(Integer setId) { this.setId = setId; }
    
    public String getSetName() { return setName; }
    public void setSetName(String setName) { this.setName = setName; }
    
    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }
    
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
}