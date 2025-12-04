package com.tradingcards.controller.api;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tradingcards.model.Card;
import com.tradingcards.repository.CardRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Trading Card API", description = "API endpoints for trading card management")
public class SimpleApiController {

    @Autowired
    private CardRepository cardRepository;

    @Operation(summary = "Get all cards with pagination")
    @GetMapping("/cards")
    public ResponseEntity<Map<String, Object>> getAllCards(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort by field") @RequestParam(defaultValue = "cardName") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String direction) {
        
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
            Page<Card> cardPage = cardRepository.findAll(pageable);
            
            Map<String, Object> response = new HashMap<>();
            response.put("cards", cardPage.getContent());
            response.put("currentPage", cardPage.getNumber());
            response.put("totalItems", cardPage.getTotalElements());
            response.put("totalPages", cardPage.getTotalPages());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return createErrorResponse("Failed to get cards: " + e.getMessage());
        }
    }

    @Operation(summary = "Get card by ID")
    @GetMapping("/cards/{id}")
    public ResponseEntity<?> getCardById(@PathVariable Integer id) {
        try {
            return cardRepository.findById(id)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> {
                        Map<String, Object> error = new HashMap<>();
                        error.put("error", "Card not found");
                        error.put("message", "Card with ID " + id + " does not exist");
                        error.put("suggestion", "Try /api/v1/cards to see available cards");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
                    });
        } catch (Exception e) {
            return createErrorResponse("Failed to get card: " + e.getMessage());
        }
    }

    @Operation(summary = "Search cards by name")
    @GetMapping("/cards/search")
    public ResponseEntity<?> searchCardsByName(
            @Parameter(description = "Search term") @RequestParam String name) {
        try {
            List<Card> cards = cardRepository.findByCardNameContainingIgnoreCase(name);
            return ResponseEntity.ok(cards);
        } catch (Exception e) {
            return createErrorResponse("Failed to search cards: " + e.getMessage());
        }
    }

    @Operation(summary = "Get cards by type")
    @GetMapping("/cards/type/{type}")
    public ResponseEntity<?> getCardsByType(@PathVariable String type) {
        try {
            List<Card> cards = cardRepository.findByCardType(type);
            return ResponseEntity.ok(cards);
        } catch (Exception e) {
            return createErrorResponse("Failed to get cards by type: " + e.getMessage());
        }
    }

    @Operation(summary = "Get cards by rarity")
    @GetMapping("/cards/rarity/{rarity}")
    public ResponseEntity<?> getCardsByRarity(@PathVariable String rarity) {
        try {
            List<Card> cards = cardRepository.findByRarity(rarity);
            return ResponseEntity.ok(cards);
        } catch (Exception e) {
            return createErrorResponse("Failed to get cards by rarity: " + e.getMessage());
        }
    }

    @Operation(summary = "Get cards by game")
    @GetMapping("/cards/game/{game}")
    public ResponseEntity<?> getCardsByGame(
            @PathVariable String game,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "20") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Card> cards = cardRepository.findByGame(game, pageable);
            
            Map<String, Object> response = new HashMap<>();
            response.put("cards", cards.getContent());
            response.put("currentPage", cards.getNumber());
            response.put("totalItems", cards.getTotalElements());
            response.put("totalPages", cards.getTotalPages());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return createErrorResponse("Failed to get cards by game: " + e.getMessage());
        }
    }

    @Operation(summary = "Get cards by set ID")
    @GetMapping("/cards/set/{setId}")
    public ResponseEntity<?> getCardsBySet(@PathVariable Integer setId) {
        try {
            List<Card> cards = cardRepository.findBySetId(setId);
            return ResponseEntity.ok(cards);
        } catch (Exception e) {
            return createErrorResponse("Failed to get cards by set: " + e.getMessage());
        }
    }

    @Operation(summary = "Advanced search with multiple criteria")
    @GetMapping("/cards/advanced-search")
    public ResponseEntity<?> advancedSearch(
            @Parameter(description = "Card name (partial match)") @RequestParam(required = false) String name,
            @Parameter(description = "Card type") @RequestParam(required = false) String type,
            @Parameter(description = "Rarity") @RequestParam(required = false) String rarity,
            @Parameter(description = "Game") @RequestParam(required = false) String game, 
            @Parameter(description = "Minimum price") @RequestParam(required = false) BigDecimal minPrice,
            @Parameter(description = "Maximum price") @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "Set ID") @RequestParam(required = false) Integer setId) {
        try {
            // Use searchCards method instead of simpleSearch
            List<Card> cards = cardRepository.searchCards(name, type, rarity, game, minPrice, maxPrice, setId);
            return ResponseEntity.ok(cards);
        } catch (Exception e) {
            return createErrorResponse("Failed to search: " + e.getMessage());
        }
    }

    @Operation(summary = "Get cards by price range")
    @GetMapping("/cards/price-range")
    public ResponseEntity<?> getCardsByPriceRange(
            @Parameter(description = "Minimum price") @RequestParam BigDecimal minPrice,
            @Parameter(description = "Maximum price") @RequestParam BigDecimal maxPrice,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        try {
            // Add Pageable parameter
            Pageable pageable = PageRequest.of(page, size);
            Page<Card> cards = cardRepository.findByPriceRange(minPrice, maxPrice, pageable);
            
            Map<String, Object> response = new HashMap<>();
            response.put("cards", cards.getContent());
            response.put("currentPage", cards.getNumber());
            response.put("totalItems", cards.getTotalElements());
            response.put("totalPages", cards.getTotalPages());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return createErrorResponse("Failed to get cards by price: " + e.getMessage());
        }
    }

    @Operation(summary = "Get most expensive cards")
    @GetMapping("/cards/most-expensive")
    public ResponseEntity<?> getMostExpensiveCards(
            @Parameter(description = "Number of cards") @RequestParam(defaultValue = "10") int limit) {
        try {
            Pageable pageable = PageRequest.of(0, limit);
            Page<Card> cards = cardRepository.findMostExpensiveCards(pageable);
            return ResponseEntity.ok(cards.getContent());
        } catch (Exception e) {
            return createErrorResponse("Failed to get most expensive cards: " + e.getMessage());
        }
    }

    @Operation(summary = "Get cheapest cards")
    @GetMapping("/cards/cheapest")
    public ResponseEntity<?> getCheapestCards(
            @Parameter(description = "Number of cards") @RequestParam(defaultValue = "10") int limit) {
        try {
            Pageable pageable = PageRequest.of(0, limit);
            Page<Card> cards = cardRepository.findCheapestCards(pageable);
            return ResponseEntity.ok(cards.getContent());
        } catch (Exception e) {
            return createErrorResponse("Failed to get cheapest cards: " + e.getMessage());
        }
    }

    @Operation(summary = "Get distinct card types")
    @GetMapping("/cards/types")
    public ResponseEntity<?> getCardTypes() {
        try {
            List<String> types = cardRepository.findDistinctCardTypes();
            return ResponseEntity.ok(types);
        } catch (Exception e) {
            return createErrorResponse("Failed to get card types: " + e.getMessage());
        }
    }

    @Operation(summary = "Get distinct rarities")
    @GetMapping("/cards/rarities")
    public ResponseEntity<?> getRarities() {
        try {
            List<String> rarities = cardRepository.findDistinctRarities();
            return ResponseEntity.ok(rarities);
        } catch (Exception e) {
            return createErrorResponse("Failed to get rarities: " + e.getMessage());
        }
    }

    @Operation(summary = "Get card statistics")
    @GetMapping("/cards/stats")
    public ResponseEntity<?> getCardStats() {
        try {
            Map<String, Object> stats = new HashMap<>();
            stats.put("totalCards", cardRepository.count());
            stats.put("averagePrice", cardRepository.getAveragePrice());
            stats.put("maxPrice", cardRepository.getMaxPrice());
            stats.put("minPrice", cardRepository.getMinPrice());
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return createErrorResponse("Failed to get card statistics: " + e.getMessage());
        }
    }

    @Operation(summary = "Health check")
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Trading Card API is running");
        response.put("version", "1.0");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "API info")
    @GetMapping("/info")
    public ResponseEntity<?> apiInfo() {
        try {
            Map<String, Object> info = new HashMap<>();
            info.put("name", "Trading Card Manager API");
            info.put("version", "1.0");
            info.put("description", "REST API for managing trading card collections");
            
            long totalCards = cardRepository.count();
            List<String> cardTypes = cardRepository.findDistinctCardTypes();
            List<String> rarities = cardRepository.findDistinctRarities();
            
            info.put("statistics", Map.of(
                "totalCards", totalCards,
                "cardTypes", cardTypes.size(),
                "rarities", rarities.size()
            ));
            
            Map<String, String> endpoints = new HashMap<>();
            endpoints.put("cards", "/api/v1/cards");
            endpoints.put("search", "/api/v1/cards/search?name={name}");
            endpoints.put("byType", "/api/v1/cards/type/{type}");
            endpoints.put("byRarity", "/api/v1/cards/rarity/{rarity}");
            endpoints.put("byGame", "/api/v1/cards/game/{game}");  
            endpoints.put("bySet", "/api/v1/cards/set/{setId}");
            endpoints.put("advancedSearch", "/api/v1/cards/advanced-search");
            endpoints.put("priceRange", "/api/v1/cards/price-range");
            endpoints.put("mostExpensive", "/api/v1/cards/most-expensive?limit={limit}");
            endpoints.put("cheapest", "/api/v1/cards/cheapest?limit={limit}");
            endpoints.put("stats", "/api/v1/cards/stats");
            endpoints.put("health", "/api/v1/health");
            
            info.put("endpoints", endpoints);
            return ResponseEntity.ok(info);
        } catch (Exception e) {
            return createErrorResponse("Failed to get API info: " + e.getMessage());
        }
    }

    @Operation(summary = "Debug - Check database contents")
    @GetMapping("/debug")
    public ResponseEntity<?> debug() {
        try {
            Map<String, Object> response = new HashMap<>();
            
            long totalCards = cardRepository.count();
            response.put("totalCards", totalCards);
            
            if (totalCards > 0) {
                List<Card> sampleCards = cardRepository.findAll(PageRequest.of(0, 5)).getContent();
                response.put("sampleCards", sampleCards);
                
                List<Integer> cardIds = cardRepository.findAll()
                        .stream()
                        .map(Card::getCardId)
                        .sorted()
                        .toList();
                response.put("cardIds", cardIds);
                
                response.put("cardTypes", cardRepository.findDistinctCardTypes());
                response.put("rarities", cardRepository.findDistinctRarities());
                
                // Add set information
                response.put("totalSets", 3); // You have 3 sets
                response.put("setIds", List.of(73, 74, 75)); // From your debug output
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return createErrorResponse("Debug failed: " + e.getMessage());
        }
    }

    private ResponseEntity<Map<String, Object>> createErrorResponse(String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Internal Server Error");
        error.put("message", message);
        error.put("timestamp", java.time.LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}