package com.tradingcards.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tradingcards.model.Card;
import com.tradingcards.model.Deck;
import com.tradingcards.model.DeckCard;
import com.tradingcards.repository.CardRepository;
import com.tradingcards.repository.DeckCardRepository;
import com.tradingcards.repository.DeckRepository;
import com.tradingcards.service.CardService;
import com.tradingcards.service.DeckService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/decks")
public class DeckController {
    
    @Autowired
    private DeckService deckService;

    @Autowired
    private DeckRepository deckRepository;
    
    @Autowired
    private DeckCardRepository deckCardRepository;
    
    @Autowired
    private CardRepository cardRepository;
    
    @Autowired
    private CardService cardService;
    
    // View all decks
    @GetMapping("")
    public String viewDecks(HttpSession session, Model model) {
        System.out.println("DEBUG: DeckController.viewDecks() called!");
        
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            System.out.println("DEBUG: No user, redirecting to login");
            return "redirect:/login";
        }
        
        System.out.println("DEBUG: User ID: " + userId);
        
        // Use the DeckService to get decks with card counts
        List<Deck> decks = deckService.getDecksWithCardCounts(userId);
        
        // Calculate total cards in all decks
        int totalCardsInDecks = decks.stream()
            .mapToInt(deck -> deck.getCardCount() != null ? deck.getCardCount() : 0)
            .sum();
        
        List<Card> allCards = cardService.getAllCards();
        
        model.addAttribute("decks", decks);
        model.addAttribute("allCards", allCards);
        model.addAttribute("totalCardsInDecks", totalCardsInDecks);
        
        System.out.println("DEBUG: Found " + decks.size() + " decks");
        System.out.println("DEBUG: Total cards in all decks: " + totalCardsInDecks);
        
        return "decks";
    }
    
    // Create new deck
    @PostMapping("/create")
    public String createDeck(@RequestParam String deckName,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            return "redirect:/login";
        }
        
        Deck newDeck = new Deck(deckName, userId);
        deckRepository.save(newDeck);
        
        redirectAttributes.addFlashAttribute("message", "Deck '" + deckName + "' created successfully!");
        return "redirect:/decks";
    }
    
    // View deck details
    @GetMapping("/{deckId}")
    public String viewDeck(@PathVariable Integer deckId, 
                          HttpSession session, 
                          Model model,
                          RedirectAttributes redirectAttributes) {
        
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            return "redirect:/login";
        }
        
        Optional<Deck> deckOptional = deckRepository.findById(deckId);
        
        if (deckOptional.isEmpty() || !deckOptional.get().getUserId().equals(userId)) {
            redirectAttributes.addFlashAttribute("error", "Deck not found or access denied!");
            return "redirect:/decks";
        }
        
        Deck deck = deckOptional.get();
        List<DeckCard> deckCards = deckCardRepository.findByDeckId(deckId);
        
        // Load card details for each deck card
        for (DeckCard deckCard : deckCards) {
            if (deckCard.getCard() == null && deckCard.getCardId() != null) {
                Card card = cardService.getCardById(deckCard.getCardId());
                deckCard.setCard(card);
            }
        }
        
        // Calculate deck statistics
        int totalCards = deckCards.stream().mapToInt(DeckCard::getQuantity).sum();
        List<Card> allCards = cardService.getAllCards();
        
        model.addAttribute("deck", deck);
        model.addAttribute("deckCards", deckCards);
        model.addAttribute("totalCards", totalCards);
        model.addAttribute("allCards", allCards);
        
        return "deck-details";
    }
    
    // Add card to deck
    @PostMapping("/{deckId}/add")
    public String addCardToDeck(@PathVariable Integer deckId,
                               @RequestParam Integer cardId,
                               @RequestParam(defaultValue = "1") Integer quantity,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            return "redirect:/login";
        }
        
        Optional<Deck> deckOptional = deckRepository.findById(deckId);
        
        if (deckOptional.isEmpty() || !deckOptional.get().getUserId().equals(userId)) {
            redirectAttributes.addFlashAttribute("error", "Deck not found or access denied!");
            return "redirect:/decks";
        }
        
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        
        if (cardOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Card not found!");
            return "redirect:/decks/" + deckId;
        }
        
        Card card = cardOptional.get();
        
        // Check if card already in deck
        List<DeckCard> existingCards = deckCardRepository.findByDeckId(deckId);
        Optional<DeckCard> existingCard = existingCards.stream()
            .filter(dc -> dc.getCardId().equals(cardId))
            .findFirst();
        
        if (existingCard.isPresent()) {
            // Update quantity
            DeckCard deckCard = existingCard.get();
            deckCard.setQuantity(deckCard.getQuantity() + quantity);
            deckCardRepository.save(deckCard);
            redirectAttributes.addFlashAttribute("message", "Updated quantity of " + card.getCardName() + " in deck!");
        } else {
            // Add new card to deck
            DeckCard deckCard = new DeckCard(deckId, cardId, quantity);
            deckCardRepository.save(deckCard);
            redirectAttributes.addFlashAttribute("message", "Added " + card.getCardName() + " to deck!");
        }
        
        return "redirect:/decks/" + deckId;
    }
    
    // Remove card from deck
    @PostMapping("/{deckId}/remove/{deckCardId}")
    public String removeCardFromDeck(@PathVariable Integer deckId,
                                    @PathVariable Integer deckCardId,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {
        
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            return "redirect:/login";
        }
        
        // Verify deck belongs to user
        Optional<Deck> deckOptional = deckRepository.findById(deckId);
        if (deckOptional.isEmpty() || !deckOptional.get().getUserId().equals(userId)) {
            redirectAttributes.addFlashAttribute("error", "Access denied!");
            return "redirect:/decks";
        }
        
        // Get card name before deleting for message
        Optional<DeckCard> deckCardOptional = deckCardRepository.findById(deckCardId);
        String cardName = "Card";
        if (deckCardOptional.isPresent()) {
            DeckCard deckCard = deckCardOptional.get();
            Card card = cardService.getCardById(deckCard.getCardId());
            if (card != null) {
                cardName = card.getCardName();
            }
        }
        
        deckCardRepository.deleteById(deckCardId);
        redirectAttributes.addFlashAttribute("message", "Removed " + cardName + " from deck!");
        
        return "redirect:/decks/" + deckId;
    }
    
    // Delete deck
    @PostMapping("/{deckId}/delete")
    public String deleteDeck(@PathVariable Integer deckId,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            return "redirect:/login";
        }
        
        // Verify deck belongs to user
        Optional<Deck> deckOptional = deckRepository.findById(deckId);
        if (deckOptional.isEmpty() || !deckOptional.get().getUserId().equals(userId)) {
            redirectAttributes.addFlashAttribute("error", "Access denied!");
            return "redirect:/decks";
        }
        
        Deck deck = deckOptional.get();
        
        // First delete all deck cards
        List<DeckCard> deckCards = deckCardRepository.findByDeckId(deckId);
        deckCardRepository.deleteAll(deckCards);
        
        // Then delete the deck
        deckRepository.deleteById(deckId);
        
        redirectAttributes.addFlashAttribute("message", "Deck '" + deck.getDeckName() + "' deleted!");
        
        return "redirect:/decks";
    }
}