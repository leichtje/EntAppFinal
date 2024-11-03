package com.teamfive.disscard;

import com.teamfive.disscard.dto.Card;
import com.teamfive.disscard.service.ICardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Controller class handling web UI and endpoints for the DissCard application
 * <h2>Front End</h2>
 * <p>
 *     Contains endpoints to generate dynamic HTML pages using Thymeleaf
 * </p>
 *
 * <h2>Back End</h2>
 * <p>
 *     Contains endpoints that handle CRUD operations to the database through
 *     the card service
 * </p>
 * @author Danny Murray
 */
@Controller
public class DissCardController {

    // ===== Fields =====

    private final ICardService cardService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // Constructor injection for better testability and immutability
    @Autowired
    public DissCardController(ICardService cardService) {
        this.cardService = cardService;
    }

    // ===== Front-end endpoints =====

    /**
     * Endpoint that generates the home page of the DissCard application's web UI
     * @param model Object used to pass data to the Thymeleaf HTML template
     * @return DissCard home page
     */
    @RequestMapping("/")
    public String index(Model model) {
        // Add data to model for use in view here

        // Return name of HTML template
        return "home";
    }

    // ===== Back-end endpoints =====

    /**
     * Fetches a list of all cards registered with DissCard
     * @return A list of JSON objects representing cards
     */
    @GetMapping(value = "/card/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Card> fetchAllCards() {
        return cardService.getAll();
    }

    /**
     * Fetches a card's info based on its ID
     * @param id ID of a card registered with DissCard
     * @return A JSON object representing a card or 404 if not found
     */
    @GetMapping(value = "/card/info/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Card> fetchCardById(@PathVariable("id") int id) {
        Card fetchedCard = cardService.getById(id);
        if (fetchedCard == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(fetchedCard, HttpStatus.OK);
    }

    /**
     * Searches DissCard's systems for a list of cards that match the specified keyword
     * @param keyword String used to filter cards
     * @return A list of card JSON objects that match the specified keyword or 404 if none found
     */
    @GetMapping(value = "/card/search/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Card>> fetchCardsByKeyword(@PathVariable("keyword") String keyword) {
        List<Card> fetchedCards = cardService.searchByName(keyword);
        if (fetchedCards == null || fetchedCards.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(fetchedCards, HttpStatus.OK);
    }

    /**
     * Adds a card to DissCard's systems
     * @param card Card to be added
     * @return A JSON object representing the card that was added or an error response if saving fails
     */
    @PostMapping(value = "/card/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Card> addCard(@RequestBody Card card) {
        Card newCard;
        try {
            newCard = cardService.save(card);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(newCard, HttpStatus.OK);
    }
}
