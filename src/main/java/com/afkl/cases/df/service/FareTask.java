package com.afkl.cases.df.service;

import com.afkl.cases.df.model.Fare;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;

import java.util.concurrent.Callable;

public class FareTask implements Callable<Fare> {

    private final UriComponents uriComponents;
    private final RestTemplate restTemplate;

    public FareTask(UriComponents uriComponents, RestTemplate restTemplate) {
        this.uriComponents = uriComponents;
        this.restTemplate = restTemplate;
    }

    @Override
    public Fare call() throws Exception {
        return restTemplate.getForObject(uriComponents.toUriString(), Fare.class);
    }
}
