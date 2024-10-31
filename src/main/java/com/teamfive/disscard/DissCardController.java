package com.teamfive.disscard;
import com.teamfive.disscard.dto.Card;
import com.teamfive.disscard.service.ICardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Controller class handling web ui and endpoints for the DissCard application
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

    @Autowired
    ICardService cardService;

    Logger log = LoggerFactory.getLogger(this.getClass());

    // ===== Front-end endpoints =====

    /**
     * Endpoint that generates the home page of the DissCard application's web UI
     * @param model Object used to pass data to the thymeleaf html template
     * @return DissCard home page
     */
    @RequestMapping("/")
    public String index(Model model) {
        // Add data to model for use in view here

        // Return name of html template to
        return "home";
    }

    // ===== Back-end endpoints =====

    /**
     * Fetches a list of all cards registered with DissCard
     * @return A list of JSON objects representing cards
     */
    @GetMapping("/card/list")
    @ResponseBody
    public List<Card> fetchAllCards() {
        return cardService.getAll();
    }

    /**
     * Fetches a card's info based on its ID
     * @param id id of a card registered with DissCard
     * @return A JSON object representing a card
     */
    @GetMapping("/card/info/{id}")
    public ResponseEntity<Card> fetchCardById(@PathVariable("id") int id) {
        Card fetchedCard = cardService.getById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(fetchedCard, headers, HttpStatus.OK);
    }

    /**
     * Searches DissCard's systems for a list of cards that match the specified keyword
     * @param keyword String used to filter cards
     * @return A list of card JSON objects that match the specified keyword
     */
    @GetMapping("/card/search/{keyword}")
    public ResponseEntity<List<Card>> fetchCardsByKeyword(@PathVariable("keyword") String keyword) {
        List<Card> fetchedCards = cardService.searchByName(keyword);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(fetchedCards, headers, HttpStatus.OK);
    }

    /**
     * Adds a card to DissCard's systems
     * @param card Card to be added
     * @return A JSON object representing the card that was added
     */
    @PostMapping(value="/card/add", consumes="application/json", produces="application/json")
    public ResponseEntity<Card> addCard(@RequestBody Card card) {
        Card newCard = null;
        HttpHeaders headers = new HttpHeaders();
        try {
            newCard = cardService.save(card);
            log.info("Card added successfully: {}", newCard);
        }
        catch (Exception e) {
            log.error("Error while adding card: {}. Exception: {}", card, e.getMessage());
            return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(newCard, headers, HttpStatus.OK);
    }
}
