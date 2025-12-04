package com.tradingcards.util;

import java.util.HashMap;
import java.util.Map;

public class CardImageMapper {
    
    private static final Map<String, String> CARD_IMAGES = new HashMap<>();
    
    static {
        // Magic: The Gathering
        CARD_IMAGES.put("Black Lotus", "https://cards.scryfall.io/large/front/b/d/bd8fa327-dd41-4737-8f19-2cf5eb1f7cdd.jpg?1614638838");
        CARD_IMAGES.put("Mox Ruby", "https://www.cardkingdom.com/images/magic-the-gathering/magic-30th-anniversary-edition/mox-ruby-not-tournament-legal-46022.jpg");
        CARD_IMAGES.put("Mox Pearl", "https://cards.scryfall.io/large/front/e/d/ed0216a0-c5c9-4a99-b869-53e4d0256326.jpg?1614638847");
        CARD_IMAGES.put("Mox Sapphire", "https://cards.scryfall.io/large/front/e/a/ea1feac0-d3a7-45eb-9719-1cdaf51ea0b6.jpg?1614638862");
        CARD_IMAGES.put("Ancestral Recall", "https://cards.scryfall.io/large/front/2/3/2398892d-28e9-4009-81ec-0d544af79d2b.jpg?1614638829");
        CARD_IMAGES.put("Time Walk", "https://cards.scryfall.io/large/front/7/0/70901356-3266-4bd9-aacc-f06c27271de5.jpg?1614638832");
        CARD_IMAGES.put("Lightning Bolt", "https://tcgplayer-cdn.tcgplayer.com/product/32656_in_1000x1000.jpg");
        CARD_IMAGES.put("Counterspell", "https://tcgplayer-cdn.tcgplayer.com/product/118445_in_1000x1000.jpg");
        CARD_IMAGES.put("Birds of Paradise", "https://tcgplayer-cdn.tcgplayer.com/product/47708_in_1000x1000.jpg");
        CARD_IMAGES.put("Shivan Dragon", "https://cards.scryfall.io/large/front/2/2/227cf1b5-f85b-41fe-be98-66e383652039.jpg?1592518393");
        CARD_IMAGES.put("Mox Jet", "https://cards.scryfall.io/large/front/5/f/5f6927e1-c580-483a-8e2a-6e2deb74800e.jpg?1614638844");
        CARD_IMAGES.put("Mox Emerald", "https://cards.scryfall.io/large/front/a/c/aced2c55-7543-4076-bcdd-36c4d649b8ae.jpg?1614638841");
        CARD_IMAGES.put("Time Twister", "https://cards.scryfall.io/png/front/f/b/fbee1e10-0b8c-44ea-b0e5-44cdd0bfcd76.png?1614638835");
        CARD_IMAGES.put("Wheel of Fortune", "https://cards.scryfall.io/png/front/f/b/fbee1e10-0b8c-44ea-b0e5-44cdd0bfcd76.png?1614638835");
        CARD_IMAGES.put("Swords to Plowshares", "https://cards.scryfall.io/png/front/f/b/fbee1e10-0b8c-44ea-b0e5-44cdd0bfcd76.png?1614638835");
        
        // Pokémon
        CARD_IMAGES.put("Charizard", "https://tcgplayer-cdn.tcgplayer.com/product/42382_in_1000x1000.jpg");
        CARD_IMAGES.put("Blastoise", "https://tcgplayer-cdn.tcgplayer.com/product/517046_in_600x600.jpg");
        CARD_IMAGES.put("Venusaur", "https://assets.pokemon.com/static-assets/content-assets/cms2/img/cards/web/ME01/ME01_EN_177.png");
        CARD_IMAGES.put("Pikachu", "https://assets.pokemon.com/static-assets/content-assets/cms2/img/cards/web/BW11/BW11_EN_RC7.png");
        CARD_IMAGES.put("Mewtwo", "https://tcgplayer-cdn.tcgplayer.com/product/477057_in_1000x1000.jpg");
        CARD_IMAGES.put("Gyarados", "https://tcgplayer-cdn.tcgplayer.com/product/101442_in_1000x1000.jpg");
        CARD_IMAGES.put("Alakazam", "https://tcgplayer-cdn.tcgplayer.com/product/517047_in_1000x1000.jpg");
        CARD_IMAGES.put("Machamp", "https://tcgplayer-cdn.tcgplayer.com/product/272422_in_1000x1000.jpg");
        CARD_IMAGES.put("Ninetales", "https://assets.pokemon.com/static-assets/content-assets/cms2/img/cards/web/SV03/SV03_EN_199.png");
        CARD_IMAGES.put("Blastoise Holo", "https://tcgplayer-cdn.tcgplayer.com/product/42464_in_1000x1000.jpg");
        CARD_IMAGES.put("Electrode", "https://tcgplayer-cdn.tcgplayer.com/product/516670_in_1000x1000.jpg");
        CARD_IMAGES.put("Flareon", "https://tcgplayer-cdn.tcgplayer.com/product/113747_in_1000x1000.jpg");
        CARD_IMAGES.put("Jolteon", "https://assets.pokemon.com/static-assets/content-assets/cms2/img/cards/web/SWSHP/SWSHP_EN_SWSH183.png");
        CARD_IMAGES.put("Mr. Mime", "https://m.media-amazon.com/images/I/4132ZQcxsPL.jpg");
        CARD_IMAGES.put("Snorlax", "https://m.media-amazon.com/images/I/51t-drI-+WL.jpg");
        
        // Yu-Gi-Oh!
        CARD_IMAGES.put("Blue-Eyes White Dragon", "https://m.media-amazon.com/images/I/71y8Hdi9ueL._AC_UF894,1000_QL80_.jpg");
        CARD_IMAGES.put("Dark Magician", "https://fbi.cults3d.com/uploaders/29168427/illustration-file/a8ce0084-aaae-4487-87fb-6d9c627455a0/Dark-Magician-SJ.jpg");
        CARD_IMAGES.put("Red-Eyes Black Dragon", "https://tcgplayer-cdn.tcgplayer.com/product/253908_in_1000x1000.jpg");
        CARD_IMAGES.put("Exodia the Forbidden One", "https://tcgplayer-cdn.tcgplayer.com/product/21954_in_1000x1000.jpg");
        CARD_IMAGES.put("Summoned Skull", "https://tcgplayer-cdn.tcgplayer.com/product/22462_in_1000x1000.jpg");
        CARD_IMAGES.put("Thousand Dragon", "https://tcgplayer-cdn.tcgplayer.com/product/22977_in_1000x1000.jpg");
        CARD_IMAGES.put("Gaia The Dragon Champion", "https://tcgplayer-cdn.tcgplayer.com/product/22002_in_1000x1000.jpg");
        CARD_IMAGES.put("Magician of Black Chaos", "https://d1htnxwo4o0jhw.cloudfront.net/spec/2219309/small/uUYz9XRVo0Gh7IUZWpB5gg.jpg");
        CARD_IMAGES.put("Gate Guardian", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTnAwu6SQn6sYLjum0WsSKS5TkSOi5DvczHRA&s");
        CARD_IMAGES.put("Kazejin", "https://tcgplayer-cdn.tcgplayer.com/product/287904_in_1000x1000.jpg");
        
        // Vanguard 
        CARD_IMAGES.put("Blaster Blade", "https://tcgplayer-cdn.tcgplayer.com/product/273648_in_1000x1000.jpg");
        CARD_IMAGES.put("Alfred Early", "https://tcgplayer-cdn.tcgplayer.com/product/168383_in_1000x1000.jpg");
        CARD_IMAGES.put("Lien", "https://tcgplayer-cdn.tcgplayer.com/product/200279_in_1000x1000.jpg");
        CARD_IMAGES.put("Gancelot", "https://tcgplayer-cdn.tcgplayer.com/product/664624_in_1000x1000.jpg");
        CARD_IMAGES.put("Pongal", "https://tcgplayer-cdn.tcgplayer.com/product/68807_in_1000x1000.jpg");
        CARD_IMAGES.put("Knight of Friendship, Kay", "https://tcgplayer-cdn.tcgplayer.com/product/184236_in_1000x1000.jpg");
        CARD_IMAGES.put("Flogal", "https://tcgplayer-cdn.tcgplayer.com/product/246492_in_1000x1000.jpg");
        CARD_IMAGES.put("Wingal", "https://tcgplayer-cdn.tcgplayer.com/product/246492_in_1000x1000.jpg");
        CARD_IMAGES.put("Knight of Loyalty, Bedivere", "https://tcgplayer-cdn.tcgplayer.com/product/273814_in_1000x1000.jpg");
        CARD_IMAGES.put("Future Knight, Llew", "https://tcgplayer-cdn.tcgplayer.com/product/68571_in_1000x1000.jpg");
    }
    
    public static String getImageUrl(String cardName) {
        return CARD_IMAGES.getOrDefault(cardName, getDefaultImageUrl(cardName));
    }
    
    private static String getDefaultImageUrl(String cardName) {
        // Fallback to a generic card image based on game type
        if (cardName.contains("Mox") || cardName.contains("Lotus") || cardName.contains("Recall")) {
            return "https://c1.scryfall.com/file/scryfall-cards/large/front/0/c/0c082aa8-bf7f-47f2-baf8-43ad253fd7d7.jpg"; // Magic default
        } else if (cardName.contains("Pokémon") || cardName.contains("Pikachu") || cardName.contains("Charizard")) {
            return "https://product-images.tcgplayer.com/fit-in/400x558/1302.jpg"; // Pokémon default
        } else if (cardName.contains("Dragon") || cardName.contains("Magician")) {
            return "https://static.wikia.nocookie.net/yugioh/images/9/9e/BlueEyesWhiteDragon-LOB-EN-UR-1E.png"; // Yu-Gi-Oh! default
        } else {
            return "https://static.wikia.nocookie.net/cardfight/images/9/94/Blaster_Blade_TD01-001.png"; // Vanguard default
        }
    }
}