package com.teamfive.disscard;

import com.teamfive.disscard.dto.Card;
import com.teamfive.disscard.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
public class DissCardController {

    @Autowired
    ICardService cardService;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/")
    public String index(Model model) {
        // Add data to model for use in view here

        // Return name of html template to
        return "home";
    }

    @GetMapping("/card/list")
    @ResponseBody
    public List<Card> fetchAllCards() {
        return cardService.getAll();
    }

    @GetMapping("/card/info/{id}")
    public ResponseEntity<Card> fetchCardById(@PathVariable("id") int id) {
        Card fetchedCard = cardService.getById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(fetchedCard, headers, HttpStatus.OK);
    }

    @GetMapping("/card/search/{keyword}")
    public ResponseEntity<List<Card>> fetchCardsByKeyword(@PathVariable("keyword") String keyword) {
        List<Card> fetchedCards = cardService.searchByName(keyword);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(fetchedCards, headers, HttpStatus.OK);
    }

    @PostMapping(value="/card/add", consumes="application/json", produces="application/json")
    public ResponseEntity<Card> addCard(@RequestBody Card card) {
        Card newCard = null;
        HttpHeaders headers = new HttpHeaders();
        try {
            newCard = cardService.save(card);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(newCard, headers, HttpStatus.OK);
    }
}
