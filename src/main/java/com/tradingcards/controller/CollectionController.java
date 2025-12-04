package com.tradingcards.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tradingcards.model.Card;
import com.tradingcards.model.CollectionItem;
import com.tradingcards.repository.CardRepository;
import com.tradingcards.repository.CollectionRepository;
import com.tradingcards.service.CardService;
import com.tradingcards.service.PaginationService;
import com.tradingcards.service.PriceCalculationService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/collection")
public class CollectionController {
    
    @Autowired
    private CollectionRepository collectionRepository;
    
    @Autowired
    private CardRepository cardRepository;
    
    @Autowired
    private CardService cardService;
    
    @Autowired
    private PriceCalculationService priceCalculationService;
    
    @Autowired
    private PaginationService paginationService;
    
    // Add this map for condition descriptions
    private Map<String, String> getConditionDescriptions() {
        Map<String, String> descriptions = new HashMap<>();
        descriptions.put("Mint", "Perfect, never played");
        descriptions.put("Near Mint", "Like new, minor imperfections");
        descriptions.put("Excellent", "Light play, still great");
        descriptions.put("Good", "Moderate play, visible wear");
        descriptions.put("Played", "Heavy play, still playable");
        descriptions.put("Poor", "Damaged, collector value low");
        return descriptions;
    }
    
    // Add this map for condition multipliers
    private Map<String, BigDecimal> getConditionMultipliers() {
        Map<String, BigDecimal> multipliers = new HashMap<>();
        multipliers.put("Mint", new BigDecimal("1.00"));
        multipliers.put("Near Mint", new BigDecimal("0.95"));
        multipliers.put("Excellent", new BigDecimal("0.85"));
        multipliers.put("Good", new BigDecimal("0.75"));
        multipliers.put("Played", new BigDecimal("0.60"));
        multipliers.put("Poor", new BigDecimal("0.40"));
        return multipliers;
    }
    
    // View user's collection with pagination
    @GetMapping("")
    public String viewCollection(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "collectionId") String sortBy,
                                @RequestParam(defaultValue = "desc") String direction,
                                HttpSession session, Model model) {
        
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            return "redirect:/login";
        }
        
        // Get paginated collection items
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        Page<CollectionItem> collectionPage = collectionRepository.findByUserId(userId, pageable);
        
        // Load card details and calculate condition prices
        BigDecimal totalValue = BigDecimal.ZERO;
        int totalCards = 0;
        
        for (CollectionItem item : collectionPage.getContent()) {
            if (item.getCard() == null && item.getCardId() != null) {
                Card card = cardService.getCardById(item.getCardId());
                item.setCard(card);
            }
            
            // Calculate or use stored condition price
            if (item.getCard() != null && item.getCard().getMarketPrice() != null) {
                BigDecimal cardValue;
                if (item.getConditionPrice() != null) {
                    cardValue = item.getConditionPrice();
                } else {
                    cardValue = priceCalculationService.calculateConditionPrice(
                        item.getCard().getMarketPrice(), item.getCondition());
                    item.setConditionPrice(cardValue);
                }
                
                totalValue = totalValue.add(cardValue.multiply(BigDecimal.valueOf(item.getQuantity())));
                totalCards += item.getQuantity();
            }
        }
        
        // Save calculated prices
        collectionRepository.saveAll(collectionPage.getContent());
        
        // Add to model
        model.addAttribute("collectionItems", collectionPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", collectionPage.getTotalPages());
        model.addAttribute("totalItems", collectionPage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        model.addAttribute("reverseDirection", direction.equals("asc") ? "desc" : "asc");
        model.addAttribute("pageNumbers", paginationService.getPageNumbers(page, collectionPage.getTotalPages()));
        
        // Add page navigation flags
        model.addAttribute("hasPrevious", collectionPage.hasPrevious());
        model.addAttribute("hasNext", collectionPage.hasNext());
        
        // Add condition information
        model.addAttribute("conditionDescriptions", getConditionDescriptions());
        model.addAttribute("conditionMultipliers", getConditionMultipliers());
        
        // Statistics
        model.addAttribute("totalValue", totalValue);
        model.addAttribute("totalCards", totalCards);
        model.addAttribute("uniqueCards", collectionRepository.getUniqueCardCount(userId));
        
        // Add username for navbar
        model.addAttribute("username", session.getAttribute("username"));
        
        return "collection";
    }
    
    // Add card to collection (treats each condition as separate)
    @PostMapping("/add/{cardId}")
    public String addToCollection(@PathVariable Integer cardId,
                                 @RequestParam(defaultValue = "1") Integer quantity,
                                 @RequestParam(defaultValue = "Mint") String condition,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            return "redirect:/login";
        }
        
        // Check if card exists
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Card not found!");
            return "redirect:/";
        }
        
        Card card = cardOptional.get();
        BigDecimal conditionPrice = priceCalculationService.calculateConditionPrice(card.getMarketPrice(), condition);
        
        // Check if same card with same condition already exists
        Optional<CollectionItem> existingItem = collectionRepository
            .findByUserIdAndCardIdAndCondition(userId, cardId, condition);
        
        if (existingItem.isPresent()) {
            // Update quantity of existing item with same condition
            CollectionItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setConditionPrice(conditionPrice);
            collectionRepository.save(item);
            redirectAttributes.addFlashAttribute("message", 
                "Updated quantity of " + card.getCardName() + " (" + condition + ") in collection!");
        } else {
            // Create new item (even if same card but different condition)
            CollectionItem newItem = new CollectionItem(userId, cardId, quantity, condition);
            newItem.setConditionPrice(conditionPrice);
            collectionRepository.save(newItem);
            redirectAttributes.addFlashAttribute("message", 
                "Added " + card.getCardName() + " (" + condition + ") to collection!");
        }
        
        return "redirect:/collection";
    }
    
    // Update collection item
    @PostMapping("/update/{collectionId}")
    public String updateCollectionItem(@PathVariable Integer collectionId,
                                      @RequestParam Integer quantity,
                                      @RequestParam String condition,
                                      HttpSession session,
                                      RedirectAttributes redirectAttributes) {
        
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            return "redirect:/login";
        }
        
        Optional<CollectionItem> itemOptional = collectionRepository.findById(collectionId);
        
        if (itemOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Collection item not found!");
            return "redirect:/collection";
        }
        
        CollectionItem item = itemOptional.get();
        
        // Verify ownership
        if (!item.getUserId().equals(userId)) {
            redirectAttributes.addFlashAttribute("error", "Access denied!");
            return "redirect:/collection";
        }
        
        // Check if changing condition creates a duplicate
        if (!item.getCondition().equals(condition)) {
            Optional<CollectionItem> duplicate = collectionRepository
                .findByUserIdAndCardIdAndCondition(userId, item.getCardId(), condition);
            
            if (duplicate.isPresent()) {
                // Merge with existing item of new condition
                CollectionItem existing = duplicate.get();
                existing.setQuantity(existing.getQuantity() + quantity);
                existing.setConditionPrice(priceCalculationService.calculateConditionPrice(
                    item.getCard().getMarketPrice(), condition));
                collectionRepository.save(existing);
                collectionRepository.delete(item);
                
                redirectAttributes.addFlashAttribute("message", 
                    "Merged with existing " + condition + " condition item!");
                return "redirect:/collection";
            }
        }
        
        item.setQuantity(quantity);
        item.setCondition(condition);
        
        // Recalculate price
        if (item.getCard() != null && item.getCard().getMarketPrice() != null) {
            BigDecimal newPrice = priceCalculationService.calculateConditionPrice(
                item.getCard().getMarketPrice(), condition);
            item.setConditionPrice(newPrice);
        }
        
        collectionRepository.save(item);
        redirectAttributes.addFlashAttribute("message", "Collection item updated!");
        
        return "redirect:/collection";
    }
    
    // Remove partial quantity from collection
    @PostMapping("/remove-quantity/{collectionId}")
    public String removePartialQuantity(@PathVariable Integer collectionId,
                                       @RequestParam(defaultValue = "1") Integer removeQuantity,
                                       @RequestParam(defaultValue = "false") Boolean removeAll,
                                       HttpSession session,
                                       RedirectAttributes redirectAttributes) {
        
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            return "redirect:/login";
        }
        
        Optional<CollectionItem> itemOptional = collectionRepository.findById(collectionId);
        
        if (itemOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Collection item not found!");
            return "redirect:/collection";
        }
        
        CollectionItem item = itemOptional.get();
        
        // Verify ownership
        if (!item.getUserId().equals(userId)) {
            redirectAttributes.addFlashAttribute("error", "Access denied!");
            return "redirect:/collection";
        }
        
        String cardName = item.getCard() != null ? item.getCard().getCardName() : "Card";
        String condition = item.getCondition();
        
        if (removeAll || item.getQuantity() <= removeQuantity) {
            // Remove entire item
            collectionRepository.delete(item);
            redirectAttributes.addFlashAttribute("message", 
                "Removed all " + cardName + " (" + condition + ") from collection!");
        } else {
            // Remove partial quantity
            int newQuantity = item.getQuantity() - removeQuantity;
            item.setQuantity(newQuantity);
            collectionRepository.save(item);
            redirectAttributes.addFlashAttribute("message", 
                "Removed " + removeQuantity + " of " + cardName + " (" + condition + ") from collection!");
        }
        
        return "redirect:/collection";
    }
    
    // Remove entire collection item
    @PostMapping("/remove/{collectionId}")
    public String removeFromCollection(@PathVariable Integer collectionId,
                                      HttpSession session,
                                      RedirectAttributes redirectAttributes) {
        
        return removePartialQuantity(collectionId, 0, true, session, redirectAttributes);
    }
    
    // Get collection statistics API (for AJAX)
    @GetMapping("/stats")
    @ResponseBody
    public CollectionStats getCollectionStats(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        CollectionStats stats = new CollectionStats();
        
        if (userId != null) {
            stats.totalCards = collectionRepository.getTotalCardCount(userId);
            stats.uniqueCards = collectionRepository.getUniqueCardCount(userId);
            stats.totalValue = collectionRepository.getTotalCollectionValue(userId);
        }
        
        return stats;
    }
    
    // Helper class for stats
    public static class CollectionStats {
        public Integer totalCards;
        public Integer uniqueCards;
        public BigDecimal totalValue;
    }
}