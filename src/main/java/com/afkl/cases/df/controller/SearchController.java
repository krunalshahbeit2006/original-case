package com.afkl.cases.df.controller;

import com.afkl.cases.df.model.Fare;
import com.afkl.cases.df.model.Location;
import com.afkl.cases.df.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private SearchService searchService;

    @RequestMapping("/")
    public String get(Model model) {
        return "searchForm";
    }

    @PostMapping("/search")
    public String retrieveFare(final Model model, @RequestParam("source") final String source, @RequestParam("destination") final String destination, final RedirectAttributes redirectAttributes) {
        LOGGER.info("retrieveFare: ");

        Fare fare = null;

        try {
            fare = searchService.retrieveFare(source, destination);
            model.addAttribute("searchResult", fare);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error fetching result");
            LOGGER.error("Exception: {}", e);
        }

        /*log.info("fare: {}", fare);*/
        LOGGER.info("fare: {}", fare);

        return "redirect:/";
    }

    @RequestMapping(value = "/autosuggest/cities", method = RequestMethod.POST)
    public Location retrieveAirport(@RequestParam final String term) {
        LOGGER.info("retrieveAirport: ");

        Location location = null;

        try {
            location = searchService.retrieveAirportLocation(term);

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*log.info("location: {}", location);*/
        LOGGER.info("location: {}", location);

        return location;
    }

}
