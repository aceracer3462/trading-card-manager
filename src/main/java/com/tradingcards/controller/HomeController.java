package com.tradingcards.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.tradingcards.service.CardService;
import com.tradingcards.service.PaginationService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    
    @Autowired
    private CardService cardService;
    
    @Autowired
    private PaginationService paginationService;
    
    @GetMapping("/")
    public String home(@RequestParam(defaultValue = "0") int page,
                      @RequestParam(defaultValue = "10") int size,
                      @RequestParam(defaultValue = "cardName") String sortBy,
                      @RequestParam(defaultValue = "asc") String direction,
                      @RequestParam(required = false) String type,
                      @RequestParam(required = false) String rarity,
                      @RequestParam(required = false) String game,
                      @RequestParam(required = false) BigDecimal minPrice,
                      @RequestParam(required = false) BigDecimal maxPrice,
                      Model model, HttpSession session) {
        
        Page<com.tradingcards.model.Card> cardPage;
        
        // Apply filters - Game filter should have highest priority
        if (game != null && !game.isEmpty()) {
            // Filter by game directly using the game field
            cardPage = cardService.filterByGame(game, 
                paginationService.createPageable(page, size, sortBy, direction));
            model.addAttribute("selectedGame", game);
        } else if (type != null && !type.isEmpty()) {
            cardPage = cardService.filterByType(type, 
                paginationService.createPageable(page, size, sortBy, direction));
            model.addAttribute("selectedType", type);
        } else if (rarity != null && !rarity.isEmpty()) {
            cardPage = cardService.filterByRarity(rarity, 
                paginationService.createPageable(page, size, sortBy, direction));
            model.addAttribute("selectedRarity", rarity);
        } else if (minPrice != null || maxPrice != null) {
            BigDecimal min = minPrice != null ? minPrice : BigDecimal.ZERO;
            BigDecimal max = maxPrice != null ? maxPrice : new BigDecimal("1000000");
            cardPage = cardService.filterByPriceRange(min, max, 
                paginationService.createPageable(page, size, sortBy, direction));
            model.addAttribute("minPrice", minPrice);
            model.addAttribute("maxPrice", maxPrice);
        } else {
            cardPage = cardService.getAllCardsPaginated(
                paginationService.createPageable(page, size, sortBy, direction));
        }
        
        populateModel(model, cardPage, page, size, sortBy, direction, session);
        
        // Add filter options
        model.addAttribute("cardTypes", cardService.getAllCardTypes());
        model.addAttribute("rarities", cardService.getAllRarities());
        model.addAttribute("games", cardService.getAllGames());
    
        return "home";
    }
    
    @GetMapping("/search")
    public String searchCards(@RequestParam(required = false) String keyword, 
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(defaultValue = "cardName") String sortBy,
                             @RequestParam(defaultValue = "asc") String direction,
                             @RequestParam(required = false) String game,
                             Model model,
                             HttpSession session) {
        
        Page<com.tradingcards.model.Card> cardPage;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            if (game != null && !game.isEmpty()) {
                // Search with game filter
                cardPage = cardService.searchWithGame(keyword, game,
                    paginationService.createPageable(page, size, sortBy, direction));
                model.addAttribute("selectedGame", game);
            } else {
                // Regular search
                cardPage = cardService.advancedSearchPaginated(keyword, 
                    paginationService.createPageable(page, size, sortBy, direction));
            }
            model.addAttribute("searchTerm", keyword);
        } else if (game != null && !game.isEmpty()) {
            // Just game filter without search
            cardPage = cardService.filterByGame(game,
                paginationService.createPageable(page, size, sortBy, direction));
            model.addAttribute("selectedGame", game);
        } else {
            cardPage = cardService.getAllCardsPaginated(
                paginationService.createPageable(page, size, sortBy, direction));
        }
        
        populateModel(model, cardPage, page, size, sortBy, direction, session);
        
        // Add filter options
        model.addAttribute("games", cardService.getAllGames());
        
        return "home";
    }
    
    @GetMapping("/card/{id}")
    public String viewCardDetails(@PathVariable Integer id, Model model, HttpSession session) {
        com.tradingcards.model.Card card = cardService.getCardById(id);
        
        if (card == null) {
            return "redirect:/";
        }
        
        model.addAttribute("card", card);
        
        // Session
        String username = (String) session.getAttribute("username");
        model.addAttribute("loggedIn", username != null);
        model.addAttribute("username", username);
        
        return "card-details";
    }
    
    private void populateModel(Model model, Page<com.tradingcards.model.Card> cardPage, 
                              int page, int size, String sortBy, String direction,
                              HttpSession session) {
        model.addAttribute("cards", cardPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", cardPage.getTotalPages());
        model.addAttribute("totalItems", cardPage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        model.addAttribute("reverseDirection", direction.equals("asc") ? "desc" : "asc");
        
        // Pagination helpers
        model.addAttribute("pageNumbers", paginationService.getPageNumbers(page, cardPage.getTotalPages()));
        model.addAttribute("hasPrevious", paginationService.hasPrevious(page));
        model.addAttribute("hasNext", paginationService.hasNext(page, cardPage.getTotalPages()));
        
        // Stats
        model.addAttribute("totalCards", cardService.getCardCount());
        model.addAttribute("highestPrice", cardService.getHighestCardPrice());
        model.addAttribute("averagePrice", cardService.getAverageCardPrice());
        
        // Session
        String username = (String) session.getAttribute("username");
        model.addAttribute("loggedIn", username != null);
        model.addAttribute("username", username);
    }
}