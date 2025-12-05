-- ============================================================================
-- Trading Card Manager - Data Initialization Script
-- Purpose: Populate all tables with sample data (15+ entries per table)
-- Note: All fields are populated, no NULL values
-- ============================================================================

-- ============================================================================
-- INSERT INTO condition_multipliers (15 entries - all conditions)
-- ============================================================================
INSERT INTO condition_multipliers (condition_name, multiplier, description) VALUES
('Mint', 1.00, 'Perfect condition, as if just pulled from pack'),
('Near Mint', 0.95, 'Minimal wear, only visible under close inspection'),
('Excellent', 0.85, 'Light wear, still tournament playable'),
('Good', 0.70, 'Moderate wear, edges may show whitening'),
('Played', 0.50, 'Heavy wear, clearly used in many games'),
('Poor', 0.25, 'Severe wear, creases, bends, or water damage'),
('Gem Mint 10', 1.00, 'PSA/BGS 10 graded - perfect in every way'),
('Mint 9', 1.00, 'PSA/BGS 9 graded - near perfect'),
('Near Mint 8', 0.95, 'PSA/BGS 8 graded - excellent condition'),
('Excellent 7', 0.85, 'PSA/BGS 7 graded - very good condition'),
('Good 6', 0.70, 'PSA/BGS 6 graded - above average'),
('Fair 5', 0.60, 'PSA/BGS 5 graded - average condition'),
('Damaged', 0.15, 'Significant damage, missing pieces, or water damage'),
('Heavily Played', 0.40, 'Major wear and tear, still recognizable'),
('Lightly Played', 0.75, 'Minor wear on edges and corners');

-- ============================================================================
-- INSERT INTO users (15 entries)
-- ============================================================================
INSERT INTO users (username, email, password_hash, join_date) VALUES
('demo', 'demo@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2023-01-15'),
('johndoe', 'john.doe@email.com', '$2a$10$encryptedPasswordHash1234567890abcdefghijklmnop', '2023-02-20'),
('janesmithcards', 'jane.smith@email.com', '$2a$10$encryptedPasswordHash2234567890abcdefghijklmnop', '2023-03-10'),
('magicmike', 'mike.magic@email.com', '$2a$10$encryptedPasswordHash3234567890abcdefghijklmnop', '2023-03-25'),
('pokemonpro', 'pokemon.pro@email.com', '$2a$10$encryptedPasswordHash4234567890abcdefghijklmnop', '2023-04-05'),
('yugiohking', 'yugi.oh@email.com', '$2a$10$encryptedPasswordHash5234567890abcdefghijklmnop', '2023-04-18'),
('cardcollector', 'collector@email.com', '$2a$10$encryptedPasswordHash6234567890abcdefghijklmnop', '2023-05-01'),
('tradingmaster', 'trading.master@email.com', '$2a$10$encryptedPasswordHash7234567890abcdefghijklmnop', '2023-05-15'),
('deckbuilder', 'deck.builder@email.com', '$2a$10$encryptedPasswordHash8234567890abcdefghijklmnop', '2023-06-01'),
('competitiveplayer', 'competitive@email.com', '$2a$10$encryptedPasswordHash9234567890abcdefghijklmnop', '2023-06-20'),
('casualgamer', 'casual.gamer@email.com', '$2a$10$encryptedPasswordHashA234567890abcdefghijklmnop', '2023-07-04'),
('rarecardsfan', 'rare.cards@email.com', '$2a$10$encryptedPasswordHashB234567890abcdefghijklmnop', '2023-07-18'),
('vanguardveteran', 'vanguard.vet@email.com', '$2a$10$encryptedPasswordHashC234567890abcdefghijklmnop', '2023-08-01'),
('proxycollector', 'proxy.collector@email.com', '$2a$10$encryptedPasswordHashD234567890abcdefghijklmnop', '2023-08-15'),
('investortrader', 'investor.trader@email.com', '$2a$10$encryptedPasswordHashE234567890abcdefghijklmnop', '2023-09-01');

-- ============================================================================
-- INSERT INTO sets (20 entries)
-- ============================================================================
INSERT INTO sets (set_name, symbol, release_date) VALUES
('Magic: The Gathering Alpha', 'A', '1993-08-05'),
('Magic: The Gathering Unlimited', 'U', '1993-12-01'),
('Magic: The Gathering Beta', 'B', '1993-10-01'),
('Magic: Dominaria United', 'DMU', '2022-09-09'),
('Pokémon Base Set', 'BS', '1999-01-09'),
('Pokémon Jungle', 'JU', '1999-06-16'),
('Pokémon Fossil', 'FO', '1999-10-10'),
('Pokémon Scarlet & Violet', 'SVI', '2023-03-31'),
('Pokémon Crown Zenith', 'CRZ', '2023-01-20'),
('Pokémon Paldea Evolved', 'PAL', '2023-06-09'),
('Yu-Gi-Oh! Legend of Blue Eyes', 'LOB', '2002-03-08'),
('Yu-Gi-Oh! Metal Raiders', 'MRD', '2002-06-27'),
('Yu-Gi-Oh! Invasion of Chaos', 'IOC', '2004-03-01'),
('Yu-Gi-Oh! Power of the Elements', 'POTE', '2022-08-05'),
('Vanguard Trial Deck', 'TD', '2011-02-26'),
('Vanguard Booster Set 01', 'BT01', '2011-08-11'),
('Vanguard Lyrical Monasterio', 'D-LBT01', '2021-11-19'),
('Magic: Modern Horizons 2', 'MH2', '2021-06-18'),
('Magic: Kamigawa Neon Dynasty', 'NEO', '2022-02-18'),
('Pokémon Team Rocket', 'TR', '2000-04-24');

-- ============================================================================
-- INSERT INTO cards (50+ entries matching your Java DataInitializer)
-- ============================================================================

-- Magic: The Gathering Alpha Set (10 cards)
INSERT INTO cards (card_name, card_type, game, mana_cost, rarity, set_id, market_price, text_box, image_url) VALUES
('Black Lotus', 'Artifact', 'Magic: The Gathering', '0', 'Rare', 1, 25000.00, 'Tap, Sacrifice Black Lotus: Add three mana of any one color.', '/images/cards/black-lotus.jpg'),
('Mox Ruby', 'Artifact', 'Magic: The Gathering', '0', 'Rare', 1, 8500.00, 'Tap: Add Red mana to your mana pool.', '/images/cards/mox-ruby.jpg'),
('Mox Pearl', 'Artifact', 'Magic: The Gathering', '0', 'Rare', 1, 8200.00, 'Tap: Add White mana to your mana pool.', '/images/cards/mox-pearl.jpg'),
('Mox Sapphire', 'Artifact', 'Magic: The Gathering', '0', 'Rare', 1, 8300.00, 'Tap: Add Blue mana to your mana pool.', '/images/cards/mox-sapphire.jpg'),
('Ancestral Recall', 'Instant', 'Magic: The Gathering', 'U', 'Rare', 1, 12000.00, 'Target player draws three cards.', '/images/cards/ancestral-recall.jpg'),
('Time Walk', 'Sorcery', 'Magic: The Gathering', '1U', 'Rare', 1, 9500.00, 'Target player takes an extra turn after this one.', '/images/cards/time-walk.jpg'),
('Lightning Bolt', 'Instant', 'Magic: The Gathering', 'R', 'Common', 1, 5.50, 'Lightning Bolt deals 3 damage to any target.', '/images/cards/lightning-bolt.jpg'),
('Counterspell', 'Instant', 'Magic: The Gathering', 'UU', 'Uncommon', 1, 8.25, 'Counter target spell.', '/images/cards/counterspell.jpg'),
('Birds of Paradise', 'Creature', 'Magic: The Gathering', 'G', 'Rare', 1, 45.00, 'Flying. Tap: Add one mana of any color.', '/images/cards/birds-of-paradise.jpg'),
('Shivan Dragon', 'Creature', 'Magic: The Gathering', '4RR', 'Rare', 1, 125.00, 'Flying. R: Shivan Dragon gets +1/+0 until end of turn.', '/images/cards/shivan-dragon.jpg');

-- Magic: The Gathering Unlimited Set (5 cards)
INSERT INTO cards (card_name, card_type, game, mana_cost, rarity, set_id, market_price, text_box, image_url) VALUES
('Mox Jet', 'Artifact', 'Magic: The Gathering', '0', 'Rare', 2, 8400.00, 'Tap: Add Black mana to your mana pool.', '/images/cards/mox-jet.jpg'),
('Mox Emerald', 'Artifact', 'Magic: The Gathering', '0', 'Rare', 2, 8100.00, 'Tap: Add Green mana to your mana pool.', '/images/cards/mox-emerald.jpg'),
('Time Twister', 'Sorcery', 'Magic: The Gathering', '2U', 'Rare', 2, 11000.00, 'Each player shuffles their hand and graveyard into their library, then draws seven cards.', '/images/cards/time-twister.jpg'),
('Wheel of Fortune', 'Sorcery', 'Magic: The Gathering', '2R', 'Rare', 2, 350.00, 'Each player discards their hand, then draws seven cards.', '/images/cards/wheel-of-fortune.jpg'),
('Swords to Plowshares', 'Instant', 'Magic: The Gathering', 'W', 'Uncommon', 2, 12.50, 'Exile target creature. Its controller gains life equal to its power.', '/images/cards/swords-to-plowshares.jpg');

-- Pokémon Base Set (10 cards)
INSERT INTO cards (card_name, card_type, game, mana_cost, rarity, set_id, market_price, text_box, image_url) VALUES
('Pikachu', 'Pokémon', 'Pokémon', 'Lightning', 'Common', 5, 12.99, 'Mouse Pokémon. When several of these Pokémon gather, their electricity could build and cause lightning storms.', '/images/cards/pikachu.jpg'),
('Charizard', 'Pokémon', 'Pokémon', 'Fire', 'Rare', 5, 89.99, 'Spits fire that is hot enough to melt boulders. Known to cause forest fires unintentionally.', '/images/cards/charizard.jpg'),
('Blastoise', 'Pokémon', 'Pokémon', 'Water', 'Rare', 5, 45.99, 'A brutal Pokémon with pressurized water jets on its shell.', '/images/cards/blastoise.jpg'),
('Blastoise Holo', 'Pokémon', 'Pokémon', 'Water', 'Holo Rare', 5, 120.00, 'The final evolution of Squirtle. It crushes its foe under its heavy body.', '/images/cards/blastoise-holo.jpg'),
('Venusaur', 'Pokémon', 'Pokémon', 'Grass', 'Rare', 5, 38.50, 'The plant blooms when it is absorbing solar energy. It stays on the move to seek sunlight.', '/images/cards/venusaur.jpg'),
('Mewtwo', 'Pokémon', 'Pokémon', 'Psychic', 'Rare', 5, 75.00, 'Its DNA is almost the same as Mew''s. However, its size and disposition are vastly different.', '/images/cards/mewtwo.jpg'),
('Gyarados', 'Pokémon', 'Pokémon', 'Water', 'Rare', 5, 32.99, 'Rarely seen in the wild. Huge and vicious, it is capable of destroying entire cities in a rage.', '/images/cards/gyarados.jpg'),
('Alakazam', 'Pokémon', 'Pokémon', 'Psychic', 'Rare', 5, 28.50, 'Its brain can outperform a supercomputer. Its IQ is said to be around 5,000.', '/images/cards/alakazam.jpg'),
('Machamp', 'Pokémon', 'Pokémon', 'Fighting', 'Rare', 5, 22.75, 'Using its heavy muscles, it throws powerful punches that can send the victim clear over the horizon.', '/images/cards/machamp.jpg'),
('Ninetales', 'Pokémon', 'Pokémon', 'Fire', 'Rare', 5, 34.99, 'Very smart and very vengeful. Grabbing one of its many tails could result in a 1000-year curse.', '/images/cards/ninetales.jpg');

-- Pokémon Jungle Set (5 cards)
INSERT INTO cards (card_name, card_type, game, mana_cost, rarity, set_id, market_price, text_box, image_url) VALUES
('Electrode', 'Pokémon', 'Pokémon', 'Lightning', 'Rare', 6, 15.75, 'It stores electric energy under very high pressure. It often explodes with little or no provocation.', '/images/cards/electrode.jpg'),
('Flareon', 'Pokémon', 'Pokémon', 'Fire', 'Rare', 6, 24.99, 'When storing thermal energy in its body, its temperature could soar to over 1600 degrees.', '/images/cards/flareon.jpg'),
('Jolteon', 'Pokémon', 'Pokémon', 'Lightning', 'Rare', 6, 26.50, 'It concentrates the weak electric charges emitted by its cells and launches wicked lightning bolts.', '/images/cards/jolteon.jpg'),
('Mr. Mime', 'Pokémon', 'Pokémon', 'Psychic', 'Rare', 6, 18.25, 'If interrupted while it is miming, it will suddenly double slap the offender with its broad hands.', '/images/cards/mr-mime.jpg'),
('Snorlax', 'Pokémon', 'Pokémon', 'Colorless', 'Rare', 6, 32.00, 'Very lazy. Just eats and sleeps. As its rotund bulk builds, it becomes steadily more slothful.', '/images/cards/snorlax.jpg');

-- Yu-Gi-Oh! Legend of Blue Eyes (5 cards)
INSERT INTO cards (card_name, card_type, game, mana_cost, rarity, set_id, market_price, text_box, image_url) VALUES
('Blue-Eyes White Dragon', 'Dragon', 'Yu-Gi-Oh!', 'None', 'Ultra Rare', 11, 45.75, 'This legendary dragon is a powerful engine of destruction.', '/images/cards/blue-eyes-white-dragon.jpg'),
('Dark Magician', 'Spellcaster', 'Yu-Gi-Oh!', 'None', 'Ultra Rare', 11, 38.50, 'The ultimate wizard in terms of attack and defense.', '/images/cards/dark-magician.jpg'),
('Red-Eyes Black Dragon', 'Dragon', 'Yu-Gi-Oh!', 'None', 'Ultra Rare', 11, 42.25, 'A ferocious dragon with a deadly attack.', '/images/cards/red-eyes-black-dragon.jpg'),
('Exodia the Forbidden One', 'Spellcaster', 'Yu-Gi-Oh!', 'None', 'Ultra Rare', 11, 150.00, 'When you have all five Exodia pieces, you win the Duel.', '/images/cards/exodia-the-forbidden-one.jpg'),
('Summoned Skull', 'Fiend', 'Yu-Gi-Oh!', 'None', 'Ultra Rare', 11, 29.99, 'A fiend with dark powers for confusing the enemy.', '/images/cards/summoned-skull.jpg');

-- Yu-Gi-Oh! Metal Raiders (5 cards)
INSERT INTO cards (card_name, card_type, game, mana_cost, rarity, set_id, market_price, text_box, image_url) VALUES
('Thousand Dragon', 'Dragon', 'Yu-Gi-Oh!', 'None', 'Ultra Rare', 12, 35.50, 'This monster gains incredible power when Time Wizard ages it 1000 years.', '/images/cards/thousand-dragon.jpg'),
('Gaia The Dragon Champion', 'Dragon', 'Yu-Gi-Oh!', 'None', 'Ultra Rare', 12, 28.75, 'Gaia the Fierce Knight armed with the power of dragons.', '/images/cards/gaia-the-dragon-champion.jpg'),
('Magician of Black Chaos', 'Spellcaster', 'Yu-Gi-Oh!', 'None', 'Ultra Rare', 12, 65.00, 'You can Ritual Summon this card with ''Black Magic Ritual''.', '/images/cards/magician-of-black-chaos.jpg'),
('Gate Guardian', 'Warrior', 'Yu-Gi-Oh!', 'None', 'Ultra Rare', 12, 42.99, 'This card cannot be Normal Summoned or Set. Must be Special Summoned.', '/images/cards/gate-guardian.jpg'),
('Kazejin', 'Spellcaster', 'Yu-Gi-Oh!', 'None', 'Ultra Rare', 12, 19.99, 'A wind warrior that hides within a tornado for protection.', '/images/cards/kazejin.jpg');

-- Vanguard Trial Deck (10 cards)
INSERT INTO cards (card_name, card_type, game, mana_cost, rarity, set_id, market_price, text_box, image_url) VALUES
('Blaster Blade', 'Royal Paladin', 'Vanguard', 'Grade 3', 'RRR', 15, 25.99, '[AUTO](VC):When this unit attacks a vanguard, this unit gets [Power]+5000 until end of that battle.', '/images/cards/blaster-blade.jpg'),
('Alfred Early', 'Royal Paladin', 'Vanguard', 'Grade 3', 'RR', 15, 18.50, '[AUTO](VC):When this unit''s attack hits a vanguard, draw a card.', '/images/cards/alfred-early.jpg'),
('Lien', 'Royal Paladin', 'Vanguard', 'Grade 0', 'C', 15, 2.99, '[AUTO]:Forerunner (When a unit of the same clan rides this unit, you may call this unit to (RC))', '/images/cards/lien.jpg'),
('Gancelot', 'Royal Paladin', 'Vanguard', 'Grade 3', 'RRR', 15, 32.75, '[CONT](VC/RC):If you have a card named "Blaster Blade" in your soul, this unit gets [Power]+2000.', '/images/cards/gancelot.jpg'),
('Pongal', 'Royal Paladin', 'Vanguard', 'Grade 1', 'C', 15, 1.99, '[AUTO]:When this unit is placed on (RC), if you have a «Royal Paladin» vanguard, draw a card.', '/images/cards/pongal.jpg'),
('Knight of Friendship, Kay', 'Royal Paladin', 'Vanguard', 'Grade 2', 'R', 15, 8.25, '[AUTO](RC):When this unit attacks, if you have a «Royal Paladin» vanguard, this unit gets [Power]+3000 until end of that battle.', '/images/cards/knight-of-friendship-kay.jpg'),
('Flogal', 'Royal Paladin', 'Vanguard', 'Grade 1', 'C', 15, 3.50, '[AUTO]:When this unit is placed on (RC), choose one of your «Royal Paladin», and that unit gets [Power]+3000 until end of turn.', '/images/cards/flogal.jpg'),
('Wingal', 'Royal Paladin', 'Vanguard', 'Grade 1', 'C', 15, 4.25, '[AUTO](RC):When this unit boosts a «Royal Paladin», the boosted unit gets [Power]+3000 until end of that battle.', '/images/cards/wingal.jpg'),
('Knight of Loyalty, Bedivere', 'Royal Paladin', 'Vanguard', 'Grade 2', 'R', 15, 9.99, '[CONT](RC):During your turn, if you have a «Royal Paladin» vanguard, this unit gets [Power]+2000.', '/images/cards/knight-of-loyalty-bedivere.jpg'),
('Future Knight, Llew', 'Royal Paladin', 'Vanguard', 'Grade 2', 'R', 15, 7.50, '[AUTO](RC):When this unit''s attack hits a vanguard, choose one of your «Royal Paladin», and that unit gets [Power]+3000 until end of turn.', '/images/cards/future-knight-llew.jpg');

-- ============================================================================
-- INSERT INTO user_collection (30+ entries - calculated with condition_price)
-- ============================================================================
INSERT INTO user_collection (user_id, card_id, quantity, condition, condition_price) VALUES
-- Demo user (user_id = 1) - 5 items
(1, 1, 1, 'Mint', 25000.00),         -- Black Lotus Mint
(1, 11, 2, 'Near Mint', 7980.00),    -- Mox Jet Near Mint (8400 * 0.95)
(1, 21, 1, 'Excellent', 76.49),      -- Venusaur Excellent (89.99 * 0.85)
(1, 31, 1, 'Good', 23.99),           -- Dark Magician Good (38.50 * 0.70 * some adjustment)
(1, 41, 2, 'Mint', 32.75),           -- Gancelot Mint

-- johndoe (user_id = 2) - 5 items
(2, 2, 1, 'Near Mint', 8075.00),     -- Mox Ruby
(2, 3, 1, 'Excellent', 6970.00),     -- Mox Pearl
(2, 7, 4, 'Near Mint', 5.23),        -- Lightning Bolt
(2, 17, 2, 'Mint', 12.99),           -- Pikachu
(2, 27, 1, 'Excellent', 42.08),      -- Gyarados

-- janesmithcards (user_id = 3) - 5 items
(3, 18, 2, 'Near Mint', 85.49),      -- Charizard
(3, 19, 1, 'Mint', 45.99),           -- Blastoise
(3, 22, 4, 'Excellent', 89.25),      -- Mewtwo (75.00 * 0.85 * qty)
(3, 36, 2, 'Near Mint', 43.48),      -- Blue-Eyes White Dragon
(3, 42, 3, 'Good', 12.95),           -- Alfred Early

-- magicmike (user_id = 4) - 5 items
(4, 8, 4, 'Near Mint', 7.84),        -- Counterspell
(4, 9, 4, 'Excellent', 38.25),       -- Birds of Paradise
(4, 10, 4, 'Near Mint', 118.75),     -- Shivan Dragon
(4, 24, 1, 'Mint', 28.50),           -- Alakazam
(4, 35, 1, 'Excellent', 27.84),      -- Summoned Skull

-- pokemonpro (user_id = 5) - 4 items
(5, 16, 3, 'Mint', 12.99),           -- Pikachu
(5, 20, 2, 'Near Mint', 43.70),      -- Blastoise Holo
(5, 25, 4, 'Excellent', 19.34),      -- Machamp
(5, 30, 2, 'Near Mint', 15.00),      -- Electrode

-- yugiohking (user_id = 6) - 4 items
(6, 32, 3, 'Near Mint', 40.14),      -- Red-Eyes Black Dragon
(6, 33, 1, 'Good', 105.00),          -- Exodia
(6, 37, 2, 'Excellent', 30.22),      -- Thousand Dragon
(6, 45, 4, 'Mint', 32.75),           -- Gancelot

-- cardcollector (user_id = 7) - 3 items
(7, 4, 1, 'Near Mint', 7885.00),     -- Mox Sapphire
(7, 15, 2, 'Excellent', 10.63),      -- Swords to Plowshares
(7, 26, 1, 'Mint', 34.99),           -- Ninetales

-- tradingmaster (user_id = 8) - 3 items
(8, 28, 3, 'Near Mint', 28.01),      -- Alakazam
(8, 38, 2, 'Excellent', 24.44),      -- Gaia The Dragon Champion
(8, 44, 2, 'Good', 1.39),            -- Pongal

-- deckbuilder (user_id = 9) - 3 items
(9, 29, 2, 'Near Mint', 25.18),      -- Jolteon
(9, 39, 1, 'Mint', 65.00),           -- Magician of Black Chaos
(9, 46, 1, 'Excellent', 2.98),       -- Knight of Friendship

-- competitiveplayer (user_id = 10) - 2 items
(10, 31, 2, 'Near Mint', 23.73),     -- Flareon
(10, 40, 1, 'Excellent', 36.54),     -- Gate Guardian

-- casualgamer (user_id = 11) - 2 items
(11, 34, 1, 'Mint', 150.00),         -- Exodia
(11, 43, 1, 'Near Mint', 2.84),      -- Lien

-- rarecardsfan (user_id = 12) - 2 items
(12, 5, 1, 'Mint', 12000.00),        -- Ancestral Recall
(12, 23, 1, 'Excellent', 102.00),    -- Venusaur

-- vanguardveteran (user_id = 13) - 2 items
(13, 41, 3, 'Near Mint', 31.11),     -- Blaster Blade
(13, 47, 2, 'Good', 2.45),           -- Flogal

-- proxycollector (user_id = 14) - 2 items
(14, 6, 1, 'Near Mint', 9025.00),    -- Time Walk
(14, 12, 1, 'Excellent', 9350.00),   -- Mox Emerald

-- investortrader (user_id = 15) - 2 items
(15, 13, 1, 'Mint', 11000.00),       -- Time Twister
(15, 14, 1, 'Near Mint', 332.50);    -- Wheel of Fortune

-- ============================================================================
-- INSERT INTO decks (20 entries)
-- ============================================================================
INSERT INTO decks (user_id, deck_name, date_created) VALUES
(1, 'Starter Deck', '2023-01-20'),
(1, 'Vintage Power', '2023-02-15'),
(2, 'Blue Control', '2023-03-01'),
(2, 'Red Aggro', '2023-03-10'),
(3, 'Charizard Deck', '2023-04-05'),
(3, 'Water Types', '2023-04-20'),
(4, 'Magic Midrange', '2023-05-01'),
(5, 'Electric Squad', '2023-05-15'),
(5, 'Fire Power', '2023-06-01'),
(6, 'Dragon Beatdown', '2023-06-10'),
(6, 'Dark Spellcasters', '2023-07-01'),
(7, 'Artifact Ramp', '2023-07-15'),
(8, 'Combo Control', '2023-08-01'),
(9, 'Tournament Ready', '2023-08-10'),
(10, 'Casual Fun', '2023-08-20'),
(11, 'Expensive Collection', '2023-09-01'),
(12, 'Budget Beaters', '2023-09-10'),
(13, 'Royal Paladins', '2023-09-15'),
(14, 'Proxy Testing', '2023-09-20'),
(15, 'Investment Portfolio', '2023-10-01');

-- ============================================================================
-- INSERT INTO deck_cards (50+ entries)
-- ============================================================================
INSERT INTO deck_cards (deck_id, card_id, quantity) VALUES
-- Starter Deck (deck_id = 1) - 7 cards
(1, 2, 2),    -- Mox Ruby
(1, 12, 1),   -- Mox Emerald
(1, 22, 1),   -- Mewtwo
(1, 32, 1),   -- Red-Eyes Black Dragon
(1, 42, 2),   -- Alfred Early
(1, 7, 4),    -- Lightning Bolt
(1, 8, 3),    -- Counterspell

-- Vintage Power (deck_id = 2) - 5 cards
(2, 1, 1),    -- Black Lotus
(2, 2, 1),    -- Mox Ruby
(2, 3, 1),    -- Mox Pearl
(2, 4, 1),    -- Mox Sapphire
(2, 5, 1),    -- Ancestral Recall

-- Blue Control (deck_id = 3) - 5 cards
(3, 8, 4),    -- Counterspell
(3, 15, 2),   -- Swords to Plowshares
(3, 9, 2),    -- Birds of Paradise
(3, 7, 3),    -- Lightning Bolt
(3, 6, 1),    -- Time Walk

-- Red Aggro (deck_id = 4) - 5 cards
(4, 7, 4),    -- Lightning Bolt
(4, 10, 3),   -- Shivan Dragon
(4, 14, 2),   -- Wheel of Fortune
(4, 2, 2),    -- Mox Ruby
(4, 11, 1),   -- Mox Jet

-- Charizard Deck (deck_id = 5) - 6 cards
(5, 17, 4),   -- Charizard
(5, 26, 2),   -- Ninetales
(5, 31, 2),   -- Flareon
(5, 16, 3),   -- Pikachu
(5, 21, 1),   -- Venusaur
(5, 25, 2),   -- Machamp

-- Water Types (deck_id = 6) - 5 cards
(6, 19, 3),   -- Blastoise
(6, 20, 2),   -- Blastoise Holo
(6, 27, 2),   -- Gyarados
(6, 18, 4),   -- Charizard
(6, 23, 1),   -- Mewtwo

-- Magic Midrange (deck_id = 7) - 5 cards
(7, 9, 3),    -- Birds of Paradise
(7, 10, 2),   -- Shivan Dragon
(7, 8, 3),    -- Counterspell
(7, 7, 4),    -- Lightning Bolt
(7, 15, 2),   -- Swords to Plowshares

-- Electric Squad (deck_id = 8) - 5 cards
(8, 16, 4),   -- Pikachu
(8, 30, 3),   -- Electrode
(8, 29, 2),   -- Jolteon
(8, 24, 2),   -- Alakazam
(8, 28, 1),   -- Mr. Mime

-- Fire Power (deck_id = 9) - 5 cards
(9, 17, 3),   -- Charizard
(9, 26, 2),   -- Ninetales
(9, 31, 3),   -- Flareon
(9, 21, 1),   -- Venusaur
(9, 18, 2),   -- Charizard (again for deck building)

-- Dragon Beatdown (deck_id = 10) - 6 cards
(10, 33, 2),  -- Blue-Eyes White Dragon
(10, 34, 1),  -- Red-Eyes Black Dragon
(10, 37, 2),  -- Thousand Dragon
(10, 38, 1),  -- Gaia The Dragon Champion
(10, 32, 2),  -- Dark Magician
(10, 36, 1),  -- Exodia

-- Dark Spellcasters (deck_id = 11) - 5 cards
(11, 32, 3),  -- Dark Magician
(11, 39, 2),  -- Magician of Black Chaos
(11, 35, 2),  -- Summoned Skull
(11, 40, 1),  -- Gate Guardian
(11, 41, 1),  -- Kazejin

-- Artifact Ramp (deck_id = 12) - 6 cards
(12, 1, 1),   -- Black Lotus
(12, 2, 1),   -- Mox Ruby
(12, 3, 1),   -- Mox Pearl
(12, 4, 1),   -- Mox Sapphire
(12, 11, 1),  -- Mox Jet
(12, 12, 1),  -- Mox Emerald

-- Combo Control (deck_id = 13) - 5 cards
(13, 5, 1),   -- Ancestral Recall
(13, 6, 1),   -- Time Walk
(13, 13, 1),  -- Time Twister
(13, 8, 4),   -- Counterspell
(13, 7, 4),   -- Lightning Bolt

-- Tournament Ready (deck_id = 14) - 5 cards
(14, 9, 4),   -- Birds of Paradise
(14, 10, 3),  -- Shivan Dragon
(14, 7, 4),   -- Lightning Bolt
(14, 8, 4),   -- Counterspell
(14, 15, 3),  -- Swords to Plowshares

-- Casual Fun (deck_id = 15) - 5 cards
(15, 16, 4),  -- Pikachu
(15, 17, 2),  -- Charizard
(15, 19, 2),  -- Blastoise
(15, 21, 2),  -- Venusaur
(15, 22, 1),  -- Mewtwo

-- Expensive Collection (deck_id = 16) - 4 cards
(16, 1, 1),   -- Black Lotus
(16, 5, 1),   -- Ancestral Recall
(16, 6, 1),   -- Time Walk
(16, 13, 1),  -- Time Twister

-- Budget Beaters (deck_id = 17) - 6 cards
(17, 7, 4),   -- Lightning Bolt
(17, 16, 4),  -- Pikachu
(17, 43, 3),  -- Lien
(17, 45, 2),  -- Pongal
(17, 47, 3),  -- Flogal
(17, 48, 2),  -- Wingal

-- Royal Paladins (deck_id = 18) - 8 cards
(18, 41, 4),  -- Blaster Blade
(18, 42, 3),  -- Alfred Early
(18, 43, 4),  -- Lien
(18, 44, 2),  -- Gancelot
(18, 45, 4),  -- Pongal
(18, 46, 3),  -- Knight of Friendship
(18, 47, 4),  -- Flogal
(18, 48, 4),  -- Wingal

-- Proxy Testing (deck_id = 19) - 5 cards
(19, 1, 1),   -- Black Lotus
(19, 2, 1),   -- Mox Ruby
(19, 5, 1),   -- Ancestral Recall
(19, 6, 1),   -- Time Walk
(19, 10, 2),  -- Shivan Dragon

-- Investment Portfolio (deck_id = 20) - 7 cards
(20, 1, 1),   -- Black Lotus
(20, 2, 1),   -- Mox Ruby
(20, 3, 1),   -- Mox Pearl
(20, 4, 1),   -- Mox Sapphire
(20, 11, 1),  -- Mox Jet
(20, 12, 1),  -- Mox Emerald
(20, 5, 1);   -- Ancestral Recall

-- ============================================================================
-- Data Validation Queries (Optional - for testing)
-- ============================================================================

-- Verify row counts
-- SELECT 'condition_multipliers' as table_name, COUNT(*) as row_count FROM condition_multipliers
-- UNION ALL
-- SELECT 'users', COUNT(*) FROM users
-- UNION ALL
-- SELECT 'sets', COUNT(*) FROM sets
-- UNION ALL
-- SELECT 'cards', COUNT(*) FROM cards
-- UNION ALL
-- SELECT 'user_collection', COUNT(*) FROM user_collection
-- UNION ALL
-- SELECT 'decks', COUNT(*) FROM decks
-- UNION ALL
-- SELECT 'deck_cards', COUNT(*) FROM deck_cards;

-- ============================================================================
-- End of Data Initialization
-- ============================================================================