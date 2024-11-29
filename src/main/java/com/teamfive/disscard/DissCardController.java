package com.teamfive.disscard;

import com.teamfive.disscard.dto.Card;
import com.teamfive.disscard.dto.PokemonApiCard;
import com.teamfive.disscard.service.ICardService;

import com.teamfive.disscard.service.IPokemonApiService;
import com.teamfive.disscard.service.PokemonApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * <h2>Front End</h2>
 * <p>
 *     Contains endpoints to generate dynamic HTML pages using Thymeleaf.
 * </p>
 * <h2>Back End</h2>
 * <p>
 *     Contains endpoints that handle CRUD operations to the database through
 *     the card service.
 * </p>
 */
@Controller
public class DissCardController {

    private static final Logger log = LoggerFactory.getLogger(DissCardController.class);

    private final ICardService cardService;
    private final IPokemonApiService pokemonApiService;

    @Autowired
    public DissCardController(ICardService cardService, IPokemonApiService pokemonApiService) {
        this.cardService = cardService;
        this.pokemonApiService = pokemonApiService;
    }

    private List<Card> allCards;

    // ===== Front-end endpoints =====

    /**
     * Endpoint that generates the home page of the DissCard application's web UI.
     *
     * @param model Object used to pass data to the Thymeleaf HTML template.
     * @return DissCard home page.
     * Endpoint that generates the home page of the DissCard application's web UI
     */
    @GetMapping("/")
    public String index(Model model) {
        // Add data to model for use in view here

        Card card;
        List<Card> topCards = new ArrayList<>() ;
        int id;
        String returnValue = "home";
        try {
            for (id = 1; id < 6; id++){
                card = cardService.getById(id);
                topCards.add(card);
                model.addAttribute("topCards", topCards);

                // Return name of HTML template
                returnValue = "home";
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Unable to retrieve cards", e);
            returnValue = "error";
        }
        return returnValue;
    }
    @GetMapping("/ExpensiveCards")
    public String expensiveCards() {

        // Add data to model for use in view here

        // Return name of HTML template
        return "ExpensiveCards";
    }
    @GetMapping("/PopularCards")
    public String PopularCards() {

        // Add data to model for use in view here

        // Return name of HTML template
        return "PopularCards";
    }
    @GetMapping("/MyCards")
    public ModelAndView MyCards() {
        // Add data to model for use in view here
            ModelAndView modelAndView = new ModelAndView();
            try {
                Iterable<Card> allCards;
                allCards = fetchAllCards();
                modelAndView.setViewName("MyCards");
                modelAndView.addObject("allCards", allCards);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Unable to retrieve cards", e);
                modelAndView.setViewName("Error");
            }
            return modelAndView;
        // Return name of HTML template

    }

    /**
     * presents page of a singular card's details
     */
    @GetMapping( "/card/info/{id}/")
    public String CardInfo(@PathVariable("id") int id, Model model) {
        // Add data to model for use in view here
       Card card = new Card();
       card = cardService.getById(id);
       model.addAttribute("card", card);
        // Return name of HTML template
        return "CardInfo";
    }
    //searching for card
    @GetMapping("/card/search/{keyword}")
    public String searchCards(@RequestParam(value="keyword", required=false, defaultValue="None")  String keyword, Model model) {
        try {
            List<Card> cards = cardService.searchByName(keyword);
            model.addAttribute("allCards", cards);
            return "MyCards";
        } catch (Exception e) {
            log.info("Search for cards failed");
            log.error(e.getMessage());
            return "error";
        }

    }

    // ===== Back-end endpoints =====

    /**
     * Fetches a list of all cards registered with DissCard.
     * @return A list of JSON objects representing cards.
     */
    @GetMapping(value = "/card/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Card> fetchAllCards() {
        try {
            log.info("Fetching all cards.");
            return cardService.getAll();
        } catch (Exception e) {
            log.info("Fetching all cards failed.");
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * Fetches a card's info based on its ID.
     * @param id ID of a card registered with DissCard.
     * @return A ResponseEntity containing a JSON object representing a card.
     * Fetches a card's info based on its ID
     */
    @GetMapping(value = "/card/info/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Card> fetchCardById(@PathVariable("id") int id) {
        try {
            log.info("Fetching card with ID: {}", id);
            Card fetchedCard = cardService.getById(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return fetchedCard != null
                    ? new ResponseEntity<>(fetchedCard, headers, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception ex) {
            log.error("Error fetching card with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Searches DissCard's systems for a list of cards that match the specified keyword.
     * @param keyword String used to filter cards.
     * @return A ResponseEntity containing a list of card JSON objects that match the specified keyword.
     */
    @GetMapping(value = "/card/search/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Card>> fetchCardsByKeyword(@PathVariable("keyword") String keyword) {
        log.info("Searching for cards with keyword: {}", keyword);
        List<Card> fetchedCardByKeyword = cardService.searchByName(keyword);

        if (fetchedCardByKeyword == null || fetchedCardByKeyword.isEmpty()) {
            log.info("No cards available: {}", keyword);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return buildResponse(fetchedCardByKeyword);
    }

    /**
     * Adds a card to DissCard's systems.
     * @param card Card to be added.
     * @return A ResponseEntity containing a JSON object representing the card that was added.
     */
    @PostMapping(value = "/card/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<Card> addCard(@RequestBody Card card) {
        log.info("Adding new card: {}", card);
        try {
            Card newCard = cardService.save(card);
            log.info("Card added successfully: {}", card);
            return buildResponse(newCard);
        } catch (Exception e) {
            log.error("Error adding card: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Searches the Pokemon TCG API for cards that match the given name
     * @param name Name of the card being searched for
     * @return List of Pokemon card objects matching the given name
     */
    @GetMapping(value = "/PokemonAPI/search/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PokemonApiCard>> searchPokemonApiCardsByName(@PathVariable("name") String name) {
        log.info("Searching Pokemon API for cards with name: {}", name);
        try {
            List<PokemonApiCard> cardList = pokemonApiService.searchCardsByName(name);
            log.info("Found {} cards", cardList.size());
            return buildResponse(cardList);
        }
        catch (Exception e) {
            log.error("Error searching Pokemon API for cards: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves a Pokemon card object from the Pokemon TCG API based on its ID
     * @param id ID of Pokemon card
     * @return Pokemon card object with the given ID
     */
    @GetMapping(value = "/PokemonAPI/card/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PokemonApiCard> fetchPokemonApiCardById(@PathVariable("id") String id) {
        log.info("Fetching card from Pokemon API with ID: {}", id);
        try {
            PokemonApiCard card = pokemonApiService.getCardById(id);
            log.info("Card retrieved from Pokemon API successfully: {}", card);
            return buildResponse(card);
        }
        catch (Exception e) {
            log.error("Error retrieving card from Pokemon API: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves the URL of the small image of a Pokemon card object from the Pokemon TCG API
     * @param id ID of Pokemon card
     * @return URL of the given card's small image
     */
    @GetMapping(value = "/PokemonAPI/image/small/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> fetchSmallPokemonApiCardImageUrlById(@PathVariable("id") String id) {
        log.info("Fetching small image from Pokemon API with card ID: {}", id);
        try {
            String smallImageUrl = pokemonApiService.getSmallImageUrlById(id);
            log.info("Small image retrieved from Pokemon API successfully: {}", smallImageUrl);
            return buildResponse(smallImageUrl);
        }
        catch (Exception e) {
            log.error("Error retrieving small image from Pokemon API: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves the URL of the large image of a Pokemon card object from the Pokemon TCG API
     * @param id ID of Pokemon card
     * @return URL of the given card's large image
     */
    @GetMapping(value = "/PokemonAPI/image/large/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> fetchLargePokemonApiCardImageUrlById(@PathVariable("id") String id) {
        log.info("Fetching large image from Pokemon API with card ID: {}", id);
        try {
            String largeImageUrl = pokemonApiService.getLargeImageUrlById(id);
            log.info("Large image retrieved from Pokemon API successfully: {}", largeImageUrl);
            return buildResponse(largeImageUrl);
        }
        catch (Exception e) {
            log.error("Error retrieving large image from Pokemon API: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Utility method to build a standardized ResponseEntity
     * @param body Body of generated ResponseEntity
     * @return Automatically generated ResponseEntity
     * @param <T> Type of body
     */
    private <T> ResponseEntity<T> buildResponse(T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }
}

