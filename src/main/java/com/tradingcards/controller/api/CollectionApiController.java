package com.tradingcards.controller.api;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tradingcards.model.CollectionItem;
import com.tradingcards.repository.CollectionRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/collections")
@Tag(name = "Collection API", description = "API endpoints for collection management")
public class CollectionApiController {

    @Autowired
    private CollectionRepository collectionRepository;

    @Operation(summary = "Get user's collection")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CollectionItem>> getUserCollection(@PathVariable Integer userId) {
        List<CollectionItem> collection = collectionRepository.findByUserId(userId);
        return ResponseEntity.ok(collection);
    }

    @Operation(summary = "Get collection statistics")
    @GetMapping("/user/{userId}/stats")
    public ResponseEntity<Map<String, Object>> getCollectionStats(@PathVariable Integer userId) {
        Integer totalCards = collectionRepository.getTotalCardCount(userId);
        Integer uniqueCards = collectionRepository.getUniqueCardCount(userId);
        BigDecimal totalValue = collectionRepository.getTotalCollectionValue(userId);
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("userId", userId);
        stats.put("totalCards", totalCards != null ? totalCards : 0);
        stats.put("uniqueCards", uniqueCards != null ? uniqueCards : 0);
        stats.put("totalValue", totalValue != null ? totalValue : BigDecimal.ZERO);
        stats.put("averageValue", totalCards != null && totalCards > 0 && totalValue != null ? 
            totalValue.divide(BigDecimal.valueOf(totalCards), 2, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO);
        
        return ResponseEntity.ok(stats);
    }

    @Operation(summary = "Get collection value by condition")
    @GetMapping("/user/{userId}/value-by-condition")
    public ResponseEntity<Map<String, Object>> getValueByCondition(@PathVariable Integer userId) {
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("message", "This endpoint would return value breakdown by condition");
        response.put("note", "Implement getValueByCondition method in CollectionRepository");
        
        return ResponseEntity.ok(response);
    }
}