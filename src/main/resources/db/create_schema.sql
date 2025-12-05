-- ============================================================================
-- Trading Card Manager Database Schema
-- Database: tradingcards
-- ============================================================================

-- Drop existing tables if they exist (in correct order due to foreign keys)
DROP TABLE IF EXISTS deck_cards CASCADE;
DROP TABLE IF EXISTS decks CASCADE;
DROP TABLE IF EXISTS user_collection CASCADE;
DROP TABLE IF EXISTS cards CASCADE;
DROP TABLE IF EXISTS sets CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS condition_multipliers CASCADE;

-- ============================================================================
-- Table: users
-- Description: User accounts and authentication
-- ============================================================================
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255),
    join_date DATE DEFAULT CURRENT_DATE,
    CHECK (LENGTH(username) >= 3),
    CHECK (email LIKE '%@%')
);

-- ============================================================================
-- Table: sets
-- Description: Stores information about card expansion sets
-- ============================================================================
CREATE TABLE sets (
    set_id SERIAL PRIMARY KEY,
    set_name VARCHAR(100) NOT NULL,
    symbol VARCHAR(50),
    release_date DATE
);

-- ============================================================================
-- Table: cards
-- Description: Main catalog of all trading cards
-- ============================================================================
CREATE TABLE cards (
    card_id SERIAL PRIMARY KEY,
    card_name VARCHAR(200) NOT NULL,
    card_type VARCHAR(50),
    mana_cost VARCHAR(20),
    market_price NUMERIC(10, 2),
    rarity VARCHAR(20),
    set_id INTEGER,
    text_box TEXT,
    image_url VARCHAR(500),
    game VARCHAR(20),
    FOREIGN KEY (set_id) REFERENCES sets(set_id) ON DELETE SET NULL,
    CHECK (market_price >= 0)
);

-- ============================================================================
-- Table: condition_multipliers
-- Description: Multipliers for card conditions (used for pricing calculations)
-- ============================================================================
CREATE TABLE condition_multipliers (
    condition_name VARCHAR(50) PRIMARY KEY,
    multiplier NUMERIC(3, 2) NOT NULL DEFAULT 1.00,
    description TEXT,
    CHECK (multiplier >= 0 AND multiplier <= 1.00)
);

-- ============================================================================
-- Table: user_collection
-- Description: Tracks cards owned by users with condition
-- ============================================================================
CREATE TABLE user_collection (
    collection_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    card_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 1,
    condition VARCHAR(20),
    condition_price NUMERIC(10, 2),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (card_id) REFERENCES cards(card_id) ON DELETE CASCADE,
    FOREIGN KEY (condition) REFERENCES condition_multipliers(condition_name) ON DELETE SET NULL,
    CHECK (quantity > 0),
    CHECK (condition_price >= 0)
);

-- ============================================================================
-- Table: decks
-- Description: User-created decks
-- ============================================================================
CREATE TABLE decks (
    deck_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    deck_name VARCHAR(100) NOT NULL,
    date_created DATE DEFAULT CURRENT_DATE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- ============================================================================
-- Table: deck_cards
-- Description: Cards included in decks
-- ============================================================================
CREATE TABLE deck_cards (
    deck_card_id SERIAL PRIMARY KEY,
    deck_id INTEGER NOT NULL,
    card_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 1,
    FOREIGN KEY (deck_id) REFERENCES decks(deck_id) ON DELETE CASCADE,
    FOREIGN KEY (card_id) REFERENCES cards(card_id) ON DELETE CASCADE,
    CHECK (quantity > 0)
);

-- ============================================================================
-- Indexes for Performance Optimization
-- ============================================================================

-- Card search indexes
CREATE INDEX idx_cards_card_name ON cards(card_name);
CREATE INDEX idx_cards_card_type ON cards(card_type);
CREATE INDEX idx_cards_rarity ON cards(rarity);
CREATE INDEX idx_cards_market_price ON cards(market_price);
CREATE INDEX idx_cards_set_id ON cards(set_id);
CREATE INDEX idx_cards_game ON cards(game);

-- User collection indexes
CREATE INDEX idx_user_collection_user_id ON user_collection(user_id);
CREATE INDEX idx_user_collection_card_id ON user_collection(card_id);
CREATE INDEX idx_user_collection_condition ON user_collection(condition);

-- Deck indexes
CREATE INDEX idx_decks_user_id ON decks(user_id);
CREATE INDEX idx_deck_cards_deck_id ON deck_cards(deck_id);
CREATE INDEX idx_deck_cards_card_id ON deck_cards(card_id);

-- ============================================================================
-- Views for Common Queries
-- ============================================================================

-- View: user_collection_value
-- Description: Calculate total collection value per user with condition pricing
CREATE VIEW user_collection_value AS
SELECT 
    u.user_id,
    u.username,
    COUNT(DISTINCT uc.card_id) as total_unique_cards,
    SUM(uc.quantity) as total_cards,
    SUM(uc.condition_price * uc.quantity) as total_value
FROM users u
LEFT JOIN user_collection uc ON u.user_id = uc.user_id
GROUP BY u.user_id, u.username;

-- View: deck_statistics
-- Description: Statistics for each deck including card count and value
CREATE VIEW deck_statistics AS
SELECT 
    d.deck_id,
    d.deck_name,
    d.user_id,
    u.username,
    d.date_created,
    COUNT(dc.card_id) as unique_cards,
    SUM(dc.quantity) as total_cards,
    SUM(c.market_price * dc.quantity) as deck_value
FROM decks d
LEFT JOIN users u ON d.user_id = u.user_id
LEFT JOIN deck_cards dc ON d.deck_id = dc.deck_id
LEFT JOIN cards c ON dc.card_id = c.card_id
GROUP BY d.deck_id, d.deck_name, d.user_id, u.username, d.date_created;

-- View: cards_with_sets
-- Description: Join cards with their set information for easier querying
CREATE VIEW cards_with_sets AS
SELECT 
    c.card_id,
    c.card_name,
    c.card_type,
    c.mana_cost,
    c.market_price,
    c.rarity,
    c.text_box,
    c.image_url,
    c.game,
    s.set_id,
    s.set_name,
    s.symbol,
    s.release_date
FROM cards c
LEFT JOIN sets s ON c.set_id = s.set_id;

-- ============================================================================
-- Triggers for Automatic Updates
-- ============================================================================

-- Trigger to automatically calculate condition_price when inserting/updating user_collection
CREATE OR REPLACE FUNCTION calculate_condition_price()
RETURNS TRIGGER AS $$
DECLARE
    base_price NUMERIC(10, 2);
    multiplier_value NUMERIC(3, 2);
BEGIN
    -- Get the market price of the card
    SELECT market_price INTO base_price
    FROM cards
    WHERE card_id = NEW.card_id;
    
    -- Get the condition multiplier
    SELECT multiplier INTO multiplier_value
    FROM condition_multipliers
    WHERE condition_name = NEW.condition;
    
    -- Calculate condition price
    IF base_price IS NOT NULL AND multiplier_value IS NOT NULL THEN
        NEW.condition_price := base_price * multiplier_value;
    END IF;
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_calculate_condition_price
BEFORE INSERT OR UPDATE ON user_collection
FOR EACH ROW
EXECUTE FUNCTION calculate_condition_price();

-- ============================================================================
-- Comments on Tables and Columns
-- ============================================================================

COMMENT ON TABLE users IS 'Stores user account information for authentication and identification';
COMMENT ON TABLE sets IS 'Contains trading card game expansion sets and editions';
COMMENT ON TABLE cards IS 'Main catalog of all trading cards across different games';
COMMENT ON TABLE condition_multipliers IS 'Defines condition grades and their price multipliers';
COMMENT ON TABLE user_collection IS 'Tracks which cards users own, quantity, and condition';
COMMENT ON TABLE decks IS 'User-created decks for different games and formats';
COMMENT ON TABLE deck_cards IS 'Junction table linking cards to decks with quantities';

COMMENT ON COLUMN cards.game IS 'Trading card game name (e.g., Magic, Pokemon, Yu-Gi-Oh!, Vanguard)';
COMMENT ON COLUMN cards.market_price IS 'Current market value in USD';
COMMENT ON COLUMN user_collection.condition_price IS 'Calculated price based on condition multiplier';
COMMENT ON COLUMN condition_multipliers.multiplier IS 'Percentage of market price (0.00 to 1.00)';

-- ============================================================================
-- Grant Permissions (Optional - adjust based on your needs)
-- ============================================================================
-- GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO tradingcards_user;
-- GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO tradingcards_user;
-- GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA public TO tradingcards_user;

-- ============================================================================
-- End of Schema Creation
-- ============================================================================