package com.afkl.cases.df.controller;

import com.afkl.cases.df.model.RequestStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by KRUNAL SHAH on 09/02/18.
 */
@RestController
public class StatisticsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    private RequestStatistics statistics;

    @RequestMapping("/statistics")
    public RequestStatistics getStats() {
        LOGGER.info("getStatistics: ");

        /*log.info("statistics: {}", statistics);*/
        LOGGER.info("statistics: {}", statistics);

        return statistics;
    }
}
