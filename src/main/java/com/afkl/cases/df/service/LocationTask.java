package com.afkl.cases.df.service;

import com.afkl.cases.df.model.Location;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;

import java.util.concurrent.Callable;

/**
 * Created by KRUNAL SHAH on 09/02/18.
 */
public class LocationTask implements Callable<Location> {

    private final UriComponents uriComponents;
    private final RestTemplate restTemplate;

    public LocationTask(final UriComponents uriComponents, final RestTemplate restTemplate) {
        this.uriComponents = uriComponents;
        this.restTemplate = restTemplate;
    }

    @Override
    public Location call() throws Exception {
        return restTemplate.getForObject(uriComponents.toUriString(), Location.class);
    }
}
