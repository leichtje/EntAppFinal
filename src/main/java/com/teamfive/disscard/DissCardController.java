package com.teamfive.disscard;

import com.teamfive.disscard.dto.Card;
import com.teamfive.disscard.service.ICardService;

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

//    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // Constructor injection for better testability and immutability

    @Autowired
    public DissCardController(ICardService cardService) {
        this.cardService = cardService;
    }

    // ===== Front-end endpoints =====

    /**

     * Endpoint that generates the home page of the DissCard application's web UI.
     * @param model Object used to pass data to the Thymeleaf HTML template.
     * @return DissCard home page.
     * Endpoint that generates the home page of the DissCard application's web UI
     */
    @GetMapping("/")
    public String index(Model model) {
        // Add data to model for use in view here
        // Return name of HTML template
        return "home";
    }
    // ===== Back-end endpoints =====
    /**
     * Fetches a list of all cards registered with DissCard.
     * @return A list of JSON objects representing cards.
     */
    @GetMapping(value = "/card/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Card> fetchAllCards() {
        log.info("Fetching all cards.");
        return cardService.getAll();
    }

    /**
     * Fetches a card's info based on its ID.
     * @param id ID of a card registered with DissCard.
     * @return A ResponseEntity containing a JSON object representing a card.
     * Fetches a card's info based on its ID
     */
    @GetMapping(value = "/card/info/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Card> fetchCardById(@PathVariable("id") int id) {
        log.info("Fetching card with ID: {}", id);
        Card fetchedCard = cardService.getById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return fetchedCard != null 
            ? new ResponseEntity<>(fetchedCard, headers, HttpStatus.OK) 
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    /**
     * Searches DissCard's systems for a list of cards that match the specified keyword.
     * @param keyword String used to filter cards.
     * @return A ResponseEntity containing a list of card JSON objects that match the specified keyword.
     
        if (fetchedCard == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(fetchedCard, HttpStatus.OK);
    }

     */
  
    @GetMapping(value = "/card/search/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Card>> fetchCardsByKeyword(@PathVariable("keyword") String keyword) {
        log.info("Searching for cards with keyword: {}", keyword);
        List<Card> fetchedCards = cardService.searchByName(keyword);
        return buildResponse(fetchedCards);
    }

    /**
     * Adds a card to DissCard's systems.
     * @param card Card to be added.
     * @return A ResponseEntity containing a JSON object representing the card that was added.

        if (fetchedCards == null || fetchedCards.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(fetchedCards, HttpStatus.OK);
    }
     */
  
    @PostMapping(value = "/card/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<Card> addCard(@RequestBody Card card) {
      
        log.info("Adding new card: {}", card);
        try {
            Card newCard = cardService.save(card);
            return buildResponse(newCard);
        } catch (Exception e) {
            log.error("Error adding card: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Utility method to build a standardized ResponseEntity
    private <T> ResponseEntity<T> buildResponse(T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(body, headers, HttpStatus.OK);


//        try {
//            Card newCard = null;
//            Card card = null;
//            newCard = cardService.save(card);
//
//
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return new ResponseEntity<>(newCard, HttpStatus.OK);
//
//            log.info("Card added successfully: {}", newCard);
//        }
//        catch (Exception e) {
//            log.error("Error while adding card: {}. Exception: {}", card, e.getMessage());
//            return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return new ResponseEntity<>(newCard, headers, HttpStatus.OK)
    }
}

