package com.tradingcards.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradingcards.model.ConditionMultiplier;
import com.tradingcards.repository.ConditionMultiplierRepository;

@Service
public class PriceCalculationService {
    
    @Autowired
    private ConditionMultiplierRepository conditionMultiplierRepository;
    
    /**
     * Calculate adjusted price based on card condition
     */
    public BigDecimal calculateConditionPrice(BigDecimal basePrice, String condition) {
        if (basePrice == null || basePrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        ConditionMultiplier multiplier = conditionMultiplierRepository
            .findByConditionName(condition)
            .orElse(new ConditionMultiplier("Mint", BigDecimal.ONE, "Default"));
        
        return basePrice.multiply(multiplier.getMultiplier())
                       .setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Get multiplier for a specific condition
     */
    public BigDecimal getMultiplierForCondition(String condition) {
        return conditionMultiplierRepository
            .findByConditionName(condition)
            .map(ConditionMultiplier::getMultiplier)
            .orElse(BigDecimal.ONE);
    }
    
    /**
     * Get all condition multipliers
     */
    public List<ConditionMultiplier> getAllConditionMultipliers() {
        return conditionMultiplierRepository.findAll();
    }
    
    /**
     * Get condition multipliers as a map for quick lookup
     */
    public Map<String, BigDecimal> getConditionMultiplierMap() {
        return conditionMultiplierRepository.findAll().stream()
            .collect(Collectors.toMap(
                ConditionMultiplier::getConditionName,
                ConditionMultiplier::getMultiplier
            ));
    }
    
    /**
     * Calculate total value with condition adjustments
     */
    public BigDecimal calculateTotalValue(BigDecimal basePrice, String condition, Integer quantity) {
        BigDecimal conditionPrice = calculateConditionPrice(basePrice, condition);
        return conditionPrice.multiply(BigDecimal.valueOf(quantity));
    }
    
    /**
     * Format price for display
     */
    public String formatPrice(BigDecimal price) {
        if (price == null) return "$0.00";
        return String.format("$%,.2f", price);
    }
    
    /**
     * Get condition description
     */
    public String getConditionDescription(String condition) {
        return conditionMultiplierRepository
            .findByConditionName(condition)
            .map(ConditionMultiplier::getDescription)
            .orElse("No description available");
    }
}