package com.tradingcards.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tradingcards.model.Card;
import com.tradingcards.model.CardSet;
import com.tradingcards.model.CollectionItem;
import com.tradingcards.model.ConditionMultiplier;
import com.tradingcards.model.Deck;
import com.tradingcards.model.DeckCard;
import com.tradingcards.model.User;
import com.tradingcards.repository.CardRepository;
import com.tradingcards.repository.CardSetRepository;
import com.tradingcards.repository.CollectionRepository;
import com.tradingcards.repository.ConditionMultiplierRepository;
import com.tradingcards.repository.DeckCardRepository;
import com.tradingcards.repository.DeckRepository;
import com.tradingcards.repository.UserRepository;
import com.tradingcards.util.CardImageMapper;
import com.tradingcards.util.PasswordHasher;

@Configuration
public class DataInitializer {
    
    @Bean
    public CommandLineRunner initData(
            CardRepository cardRepository,
            UserRepository userRepository,
            DeckRepository deckRepository,
            DeckCardRepository deckCardRepository,
            CardSetRepository cardSetRepository,
            CollectionRepository collectionRepository,
            ConditionMultiplierRepository conditionMultiplierRepository) {
        
        return args -> {
            System.out.println("🚀 STARTING DATABASE INITIALIZATION...");
            
            // =============================================
            // IMPORTANT: Check if data already exists
            // =============================================
            long userCount = userRepository.count();
            long cardCount = cardRepository.count();
            long cardSetCount = cardSetRepository.count();
            
            System.out.println("📊 Current Database Status:");
            System.out.println("   Users: " + userCount);
            System.out.println("   Cards: " + cardCount);
            System.out.println("   Card Sets: " + cardSetCount);
            
            // Check if demo user exists
            boolean demoUserExists = userRepository.findByUsername("demo").isPresent();
            System.out.println("   Demo User Exists: " + demoUserExists);
            
            // =============================================
            // STRATEGY: Only initialize if database is empty
            // =============================================
            if (cardCount > 0 && cardSetCount > 0 && userCount > 0) {
                System.out.println("✅ Database already has data. Skipping initialization.");
                System.out.println("   Existing user data will be preserved.");
                
                // Only create demo user if it doesn't exist (but other users do)
                if (!demoUserExists && userCount > 0) {
                    System.out.println("   Creating demo user only...");
                    User demoUser = new User("demo", "demo@example.com", PasswordHasher.hashPassword("demo123"));
                    userRepository.save(demoUser);
                    System.out.println("   ✅ Created demo user: demo/demo123");
                }
                
                return; // EXIT - don't clear or reinitialize existing data
            }
            
            // =============================================
            // Only clear if we absolutely need to reinitialize
            // =============================================
            if (cardCount > 0 || cardSetCount > 0 || userCount > 0) {
                System.out.println("⚠️  Partial data found. Cleaning up for fresh initialization...");
                
                // Clear in proper order (child tables first)
                collectionRepository.deleteAll();      // Depends on users and cards
                deckCardRepository.deleteAll();        // Depends on decks and cards
                deckRepository.deleteAll();            // Depends on users
                cardRepository.deleteAll();            // Depends on sets
                conditionMultiplierRepository.deleteAll();
                cardSetRepository.deleteAll();
                
                // Only delete non-demo users
                List<User> allUsers = userRepository.findAll();
                for (User user : allUsers) {
                    if (!"demo".equals(user.getUsername())) {
                        userRepository.delete(user);
                    }
                }
                
                System.out.println("✅ Old data cleared (demo user preserved if existed)");
            }
            
            // =============================================
            // Create fresh data
            // =============================================
            
            // 1. Create card sets
            System.out.println("Creating card sets...");
            CardSet alpha = new CardSet("Magic: The Gathering Alpha", LocalDate.of(1993, 8, 5), "A");
            CardSet baseSet = new CardSet("Pokémon Base Set", LocalDate.of(1999, 1, 9), "BS");
            CardSet legendary = new CardSet("Yu-Gi-Oh! Legend of Blue Eyes", LocalDate.of(2002, 3, 8), "LOB");
            CardSet unlimited = new CardSet("Magic: The Gathering Unlimited", LocalDate.of(1993, 12, 1), "U");
            CardSet jungle = new CardSet("Pokémon Jungle", LocalDate.of(1999, 6, 16), "JU");
            CardSet metalRaiders = new CardSet("Yu-Gi-Oh! Metal Raiders", LocalDate.of(2002, 6, 27), "MRD");
            CardSet vanguardTd = new CardSet("Vanguard Trial Deck", LocalDate.of(2011, 2, 26), "TD");
            
            alpha = cardSetRepository.save(alpha);
            baseSet = cardSetRepository.save(baseSet);
            legendary = cardSetRepository.save(legendary);
            unlimited = cardSetRepository.save(unlimited);
            jungle = cardSetRepository.save(jungle);
            metalRaiders = cardSetRepository.save(metalRaiders);
            vanguardTd = cardSetRepository.save(vanguardTd);
            
            // 2. Create condition multipliers
            System.out.println("Creating condition multipliers...");
            List<ConditionMultiplier> multipliers = List.of(
                new ConditionMultiplier("Mint", new BigDecimal("1.00"), "Perfect condition"),
                new ConditionMultiplier("Near Mint", new BigDecimal("0.95"), "Almost perfect"),
                new ConditionMultiplier("Excellent", new BigDecimal("0.85"), "Lightly played"),
                new ConditionMultiplier("Good", new BigDecimal("0.70"), "Moderately played"),
                new ConditionMultiplier("Played", new BigDecimal("0.50"), "Heavily played"),
                new ConditionMultiplier("Poor", new BigDecimal("0.25"), "Damaged")
            );
            conditionMultiplierRepository.saveAll(multipliers);
            
            // 3. Create ALL 50 cards
            System.out.println("Creating 50 sample cards with images...");
            
            List<Card> cards = new ArrayList<>();
            
            // ===== MAGIC: THE GATHERING CARDS (15 total) =====
            
            // Alpha Set (10 cards)
            cards.add(new Card("Black Lotus", "Artifact", "Magic: The Gathering", "0", "Rare", 
                alpha, new BigDecimal("25000.00"), 
                "Tap, Sacrifice Black Lotus: Add three mana of any one color.",
                CardImageMapper.getImageUrl("Black Lotus")));
            
            cards.add(new Card("Mox Ruby", "Artifact", "Magic: The Gathering", "0", "Rare", 
                alpha, new BigDecimal("8500.00"), 
                "Tap: Add Red mana to your mana pool.",
                CardImageMapper.getImageUrl("Mox Ruby")));
            
            cards.add(new Card("Mox Pearl", "Artifact", "Magic: The Gathering", "0", "Rare", 
                alpha, new BigDecimal("8200.00"), 
                "Tap: Add White mana to your mana pool.",
                CardImageMapper.getImageUrl("Mox Pearl")));
            
            cards.add(new Card("Mox Sapphire", "Artifact", "Magic: The Gathering", "0", "Rare", 
                alpha, new BigDecimal("8300.00"), 
                "Tap: Add Blue mana to your mana pool.",
                CardImageMapper.getImageUrl("Mox Sapphire")));
            
            cards.add(new Card("Ancestral Recall", "Instant", "Magic: The Gathering", "U", "Rare", 
                alpha, new BigDecimal("12000.00"), 
                "Target player draws three cards.",
                CardImageMapper.getImageUrl("Ancestral Recall")));
            
            cards.add(new Card("Time Walk", "Sorcery", "Magic: The Gathering", "1U", "Rare", 
                alpha, new BigDecimal("9500.00"), 
                "Target player takes an extra turn after this one.",
                CardImageMapper.getImageUrl("Time Walk")));
            
            cards.add(new Card("Lightning Bolt", "Instant", "Magic: The Gathering", "R", "Common", 
                alpha, new BigDecimal("5.50"), 
                "Lightning Bolt deals 3 damage to any target.",
                CardImageMapper.getImageUrl("Lightning Bolt")));
            
            cards.add(new Card("Counterspell", "Instant", "Magic: The Gathering", "UU", "Uncommon", 
                alpha, new BigDecimal("8.25"), 
                "Counter target spell.",
                CardImageMapper.getImageUrl("Counterspell")));
            
            cards.add(new Card("Birds of Paradise", "Creature", "Magic: The Gathering", "G", "Rare", 
                alpha, new BigDecimal("45.00"), 
                "Flying. Tap: Add one mana of any color.",
                CardImageMapper.getImageUrl("Birds of Paradise")));
            
            cards.add(new Card("Shivan Dragon", "Creature", "Magic: The Gathering", "4RR", "Rare", 
                alpha, new BigDecimal("125.00"), 
                "Flying. R: Shivan Dragon gets +1/+0 until end of turn.",
                CardImageMapper.getImageUrl("Shivan Dragon")));
            
            // Unlimited Set (5 cards)
            cards.add(new Card("Mox Jet", "Artifact", "Magic: The Gathering", "0", "Rare", 
                unlimited, new BigDecimal("8400.00"), 
                "Tap: Add Black mana to your mana pool.",
                CardImageMapper.getImageUrl("Mox Jet")));
            
            cards.add(new Card("Mox Emerald", "Artifact", "Magic: The Gathering", "0", "Rare", 
                unlimited, new BigDecimal("8100.00"), 
                "Tap: Add Green mana to your mana pool.",
                CardImageMapper.getImageUrl("Mox Emerald")));
            
            cards.add(new Card("Time Twister", "Sorcery", "Magic: The Gathering", "2U", "Rare", 
                unlimited, new BigDecimal("11000.00"), 
                "Each player shuffles their hand and graveyard into their library, then draws seven cards.",
                CardImageMapper.getImageUrl("Time Twister")));
            
            cards.add(new Card("Wheel of Fortune", "Sorcery", "Magic: The Gathering", "2R", "Rare", 
                unlimited, new BigDecimal("350.00"), 
                "Each player discards their hand, then draws seven cards.",
                CardImageMapper.getImageUrl("Wheel of Fortune")));
            
            cards.add(new Card("Swords to Plowshares", "Instant", "Magic: The Gathering", "W", "Uncommon", 
                unlimited, new BigDecimal("12.50"), 
                "Exile target creature. Its controller gains life equal to its power.",
                CardImageMapper.getImageUrl("Swords to Plowshares")));
            
            // ===== POKÉMON CARDS (15 total) =====
            
            // Base Set (10 cards)
            cards.add(new Card("Pikachu", "Pokémon", "Pokémon", "Lightning", "Common", 
                baseSet, new BigDecimal("12.99"), 
                "Mouse Pokémon. When several of these Pokémon gather, their electricity could build and cause lightning storms.",
                CardImageMapper.getImageUrl("Pikachu")));
            
            cards.add(new Card("Charizard", "Pokémon", "Pokémon", "Fire", "Rare", 
                baseSet, new BigDecimal("89.99"), 
                "Spits fire that is hot enough to melt boulders. Known to cause forest fires unintentionally.",
                CardImageMapper.getImageUrl("Charizard")));
            
            cards.add(new Card("Blastoise", "Pokémon", "Pokémon", "Water", "Rare", 
                baseSet, new BigDecimal("45.99"), 
                "A brutal Pokémon with pressurized water jets on its shell.",
                CardImageMapper.getImageUrl("Blastoise")));
            
            cards.add(new Card("Blastoise Holo", "Pokémon", "Pokémon", "Water", "Holo Rare", 
                baseSet, new BigDecimal("120.00"), 
                "The final evolution of Squirtle. It crushes its foe under its heavy body.",
                CardImageMapper.getImageUrl("Blastoise Holo")));
            
            cards.add(new Card("Venusaur", "Pokémon", "Pokémon", "Grass", "Rare", 
                baseSet, new BigDecimal("38.50"), 
                "The plant blooms when it is absorbing solar energy. It stays on the move to seek sunlight.",
                CardImageMapper.getImageUrl("Venusaur")));
            
            cards.add(new Card("Mewtwo", "Pokémon", "Pokémon", "Psychic", "Rare", 
                baseSet, new BigDecimal("75.00"), 
                "Its DNA is almost the same as Mew's. However, its size and disposition are vastly different.",
                CardImageMapper.getImageUrl("Mewtwo")));
            
            cards.add(new Card("Gyarados", "Pokémon", "Pokémon", "Water", "Rare", 
                baseSet, new BigDecimal("32.99"), 
                "Rarely seen in the wild. Huge and vicious, it is capable of destroying entire cities in a rage.",
                CardImageMapper.getImageUrl("Gyarados")));
            
            cards.add(new Card("Alakazam", "Pokémon", "Pokémon", "Psychic", "Rare", 
                baseSet, new BigDecimal("28.50"), 
                "Its brain can outperform a supercomputer. Its IQ is said to be around 5,000.",
                CardImageMapper.getImageUrl("Alakazam")));
            
            cards.add(new Card("Machamp", "Pokémon", "Pokémon", "Fighting", "Rare", 
                baseSet, new BigDecimal("22.75"), 
                "Using its heavy muscles, it throws powerful punches that can send the victim clear over the horizon.",
                CardImageMapper.getImageUrl("Machamp")));
            
            cards.add(new Card("Ninetales", "Pokémon", "Pokémon", "Fire", "Rare", 
                baseSet, new BigDecimal("34.99"), 
                "Very smart and very vengeful. Grabbing one of its many tails could result in a 1000-year curse.",
                CardImageMapper.getImageUrl("Ninetales")));
            
            // Jungle Set (5 cards)
            cards.add(new Card("Electrode", "Pokémon", "Pokémon", "Lightning", "Rare", 
                jungle, new BigDecimal("15.75"), 
                "It stores electric energy under very high pressure. It often explodes with little or no provocation.",
                CardImageMapper.getImageUrl("Electrode")));
            
            cards.add(new Card("Flareon", "Pokémon", "Pokémon", "Fire", "Rare", 
                jungle, new BigDecimal("24.99"), 
                "When storing thermal energy in its body, its temperature could soar to over 1600 degrees.",
                CardImageMapper.getImageUrl("Flareon")));
            
            cards.add(new Card("Jolteon", "Pokémon", "Pokémon", "Lightning", "Rare", 
                jungle, new BigDecimal("26.50"), 
                "It concentrates the weak electric charges emitted by its cells and launches wicked lightning bolts.",
                CardImageMapper.getImageUrl("Jolteon")));
            
            cards.add(new Card("Mr. Mime", "Pokémon", "Pokémon", "Psychic", "Rare", 
                jungle, new BigDecimal("18.25"), 
                "If interrupted while it is miming, it will suddenly double slap the offender with its broad hands.",
                CardImageMapper.getImageUrl("Mr. Mime")));
            
            cards.add(new Card("Snorlax", "Pokémon", "Pokémon", "Colorless", "Rare", 
                jungle, new BigDecimal("32.00"), 
                "Very lazy. Just eats and sleeps. As its rotund bulk builds, it becomes steadily more slothful.",
                CardImageMapper.getImageUrl("Snorlax")));
            
            // ===== YU-GI-OH! CARDS (10 total) =====
            
            // Legend of Blue Eyes (5 cards)
            cards.add(new Card("Blue-Eyes White Dragon", "Dragon","Yu-Gi-Oh!", "None", "Ultra Rare", 
                legendary, new BigDecimal("45.75"), 
                "This legendary dragon is a powerful engine of destruction.",
                CardImageMapper.getImageUrl("Blue-Eyes White Dragon")));
            
            cards.add(new Card("Dark Magician", "Spellcaster", "Yu-Gi-Oh!", "None", "Ultra Rare", 
                legendary, new BigDecimal("38.50"), 
                "The ultimate wizard in terms of attack and defense.",
                CardImageMapper.getImageUrl("Dark Magician")));
            
            cards.add(new Card("Red-Eyes Black Dragon", "Dragon", "Yu-Gi-Oh!", "None", "Ultra Rare", 
                legendary, new BigDecimal("42.25"), 
                "A ferocious dragon with a deadly attack.",
                CardImageMapper.getImageUrl("Red-Eyes Black Dragon")));
            
            cards.add(new Card("Exodia the Forbidden One", "Spellcaster", "Yu-Gi-Oh!", "None", "Ultra Rare", 
                legendary, new BigDecimal("150.00"), 
                "When you have all five Exodia pieces, you win the Duel.",
                CardImageMapper.getImageUrl("Exodia the Forbidden One")));
            
            cards.add(new Card("Summoned Skull", "Fiend", "Yu-Gi-Oh!", "None", "Ultra Rare", 
                legendary, new BigDecimal("29.99"), 
                "A fiend with dark powers for confusing the enemy.",
                CardImageMapper.getImageUrl("Summoned Skull")));
            
            // Metal Raiders (5 cards)
            cards.add(new Card("Thousand Dragon", "Dragon", "Yu-Gi-Oh!", "None", "Ultra Rare", 
                metalRaiders, new BigDecimal("35.50"), 
                "This monster gains incredible power when Time Wizard ages it 1000 years.",
                CardImageMapper.getImageUrl("Thousand Dragon")));
            
            cards.add(new Card("Gaia The Dragon Champion", "Dragon", "Yu-Gi-Oh!", "None", "Ultra Rare", 
                metalRaiders, new BigDecimal("28.75"), 
                "Gaia the Fierce Knight armed with the power of dragons.",
                CardImageMapper.getImageUrl("Gaia The Dragon Champion")));
            
            cards.add(new Card("Magician of Black Chaos", "Spellcaster", "Yu-Gi-Oh!", "None", "Ultra Rare", 
                metalRaiders, new BigDecimal("65.00"), 
                "You can Ritual Summon this card with 'Black Magic Ritual'.",
                CardImageMapper.getImageUrl("Magician of Black Chaos")));
            
            cards.add(new Card("Gate Guardian", "Warrior", "Yu-Gi-Oh!", "None", "Ultra Rare", 
                metalRaiders, new BigDecimal("42.99"), 
                "This card cannot be Normal Summoned or Set. Must be Special Summoned.",
                CardImageMapper.getImageUrl("Gate Guardian")));
            
            cards.add(new Card("Kazejin", "Spellcaster", "Yu-Gi-Oh!", "None", "Ultra Rare", 
                metalRaiders, new BigDecimal("19.99"), 
                "A wind warrior that hides within a tornado for protection.",
                CardImageMapper.getImageUrl("Kazejin")));
            
            // ===== VANGUARD CARDS (10 total) =====
            
            // Vanguard Trial Deck
            cards.add(new Card("Blaster Blade", "Royal Paladin","Vanguard", "Grade 3", "RRR", 
                vanguardTd, new BigDecimal("25.99"), 
                "[AUTO](VC):When this unit attacks a vanguard, this unit gets [Power]+5000 until end of that battle.",
                CardImageMapper.getImageUrl("Blaster Blade")));
            
            cards.add(new Card("Alfred Early", "Royal Paladin","Vanguard", "Grade 3", "RR", 
                vanguardTd, new BigDecimal("18.50"), 
                "[AUTO](VC):When this unit's attack hits a vanguard, draw a card.",
                CardImageMapper.getImageUrl("Alfred Early")));
            
            cards.add(new Card("Lien", "Royal Paladin","Vanguard", "Grade 0", "C", 
                vanguardTd, new BigDecimal("2.99"), 
                "[AUTO]:Forerunner (When a unit of the same clan rides this unit, you may call this unit to (RC))",
                CardImageMapper.getImageUrl("Lien")));
            
            cards.add(new Card("Gancelot", "Royal Paladin","Vanguard", "Grade 3", "RRR", 
                vanguardTd, new BigDecimal("32.75"), 
                "[CONT](VC/RC):If you have a card named \"Blaster Blade\" in your soul, this unit gets [Power]+2000.",
                CardImageMapper.getImageUrl("Gancelot")));
            
            cards.add(new Card("Pongal", "Royal Paladin","Vanguard", "Grade 1", "C", 
                vanguardTd, new BigDecimal("1.99"), 
                "[AUTO]:When this unit is placed on (RC), if you have a «Royal Paladin» vanguard, draw a card.",
                CardImageMapper.getImageUrl("Pongal")));
            
            cards.add(new Card("Knight of Friendship, Kay", "Royal Paladin","Vanguard", "Grade 2", "R", 
                vanguardTd, new BigDecimal("8.25"), 
                "[AUTO](RC):When this unit attacks, if you have a «Royal Paladin» vanguard, this unit gets [Power]+3000 until end of that battle.",
                CardImageMapper.getImageUrl("Knight of Friendship, Kay")));
            
            cards.add(new Card("Flogal", "Royal Paladin","Vanguard", "Grade 1", "C", 
                vanguardTd, new BigDecimal("3.50"), 
                "[AUTO]:When this unit is placed on (RC), choose one of your «Royal Paladin», and that unit gets [Power]+3000 until end of turn.",
                CardImageMapper.getImageUrl("Flogal")));
            
            cards.add(new Card("Wingal", "Royal Paladin","Vanguard", "Grade 1", "C", 
                vanguardTd, new BigDecimal("4.25"), 
                "[AUTO](RC):When this unit boosts a «Royal Paladin», the boosted unit gets [Power]+3000 until end of that battle.",
                CardImageMapper.getImageUrl("Wingal")));
            
            cards.add(new Card("Knight of Loyalty, Bedivere", "Royal Paladin","Vanguard", "Grade 2", "R", 
                vanguardTd, new BigDecimal("9.99"), 
                "[CONT](RC):During your turn, if you have a «Royal Paladin» vanguard, this unit gets [Power]+2000.",
                CardImageMapper.getImageUrl("Knight of Loyalty, Bedivere")));
            
            cards.add(new Card("Future Knight, Llew", "Royal Paladin","Vanguard", "Grade 2", "R", 
                vanguardTd, new BigDecimal("7.50"), 
                "[AUTO](RC):When this unit's attack hits a vanguard, choose one of your «Royal Paladin», and that unit gets [Power]+3000 until end of turn.",
                CardImageMapper.getImageUrl("Future Knight, Llew")));
            
            // Save all cards
            cardRepository.saveAll(cards);
            System.out.println("✅ Created " + cards.size() + " sample cards with images!");
            
            // 4. Create demo user (only if it doesn't exist)
            if (!demoUserExists) {
                System.out.println("Creating demo user...");
                User demoUser = new User("demo", "demo@example.com", PasswordHasher.hashPassword("demo123"));
                demoUser = userRepository.save(demoUser);
                System.out.println("✅ Created demo user: demo/demo123");
                
                // 5. Create sample collections for demo user
                System.out.println("Adding sample cards to demo collection...");
                CollectionItem item1 = new CollectionItem(demoUser.getUserId(), cards.get(0).getCardId(), 1, "Mint");
                CollectionItem item2 = new CollectionItem(demoUser.getUserId(), cards.get(10).getCardId(), 2, "Near Mint");
                CollectionItem item3 = new CollectionItem(demoUser.getUserId(), cards.get(20).getCardId(), 1, "Excellent");
                CollectionItem item4 = new CollectionItem(demoUser.getUserId(), cards.get(30).getCardId(), 1, "Good");
                CollectionItem item5 = new CollectionItem(demoUser.getUserId(), cards.get(40).getCardId(), 2, "Mint");
                
                collectionRepository.save(item1);
                collectionRepository.save(item2);
                collectionRepository.save(item3);
                collectionRepository.save(item4);
                collectionRepository.save(item5);
                System.out.println("✅ Added 5 sample cards to demo collection");
                
                // 6. Create sample deck for demo user
                System.out.println("Creating sample deck...");
                Deck sampleDeck = new Deck("Starter Deck", demoUser.getUserId());
                sampleDeck = deckRepository.save(sampleDeck);
                System.out.println("✅ Created sample deck");
                
                // 7. Add some cards to sample deck
                System.out.println("Adding cards to sample deck...");
                DeckCard deckCard1 = new DeckCard(sampleDeck.getDeckId(), cards.get(1).getCardId(), 2);
                DeckCard deckCard2 = new DeckCard(sampleDeck.getDeckId(), cards.get(11).getCardId(), 1);
                DeckCard deckCard3 = new DeckCard(sampleDeck.getDeckId(), cards.get(21).getCardId(), 1);
                DeckCard deckCard4 = new DeckCard(sampleDeck.getDeckId(), cards.get(31).getCardId(), 1);
                DeckCard deckCard5 = new DeckCard(sampleDeck.getDeckId(), cards.get(41).getCardId(), 2);
                
                deckCardRepository.save(deckCard1);
                deckCardRepository.save(deckCard2);
                deckCardRepository.save(deckCard3);
                deckCardRepository.save(deckCard4);
                deckCardRepository.save(deckCard5);
                System.out.println("✅ Added 5 sample cards to deck");
            }
            
            System.out.println("🎉 DATABASE INITIALIZATION COMPLETE!");
            System.out.println("======================================");
            System.out.println("Total Cards: " + cardRepository.count());
            System.out.println("Total Sets: " + cardSetRepository.count());
            System.out.println("Total Users: " + userRepository.count());
            System.out.println("Total Decks: " + deckRepository.count());
            System.out.println("Total Collection Items: " + collectionRepository.count());
            System.out.println("Total Deck Cards: " + deckCardRepository.count());
            System.out.println("======================================");
        };
    }
}