package com.afkl.cases.df.service;

import com.afkl.cases.df.model.Fare;
import com.afkl.cases.df.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*@Slf4j*/
@Service
public class SearchServiceImpl implements SearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchServiceImpl.class);

    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    //@Autowired
    RestTemplate restTemplate = new RestTemplate();

    @Value("${service.mockurl}")
    private String serviceUrl;

    @Value("${service.accessToken}")
    private String accessToken;

    @Override
    public Location retrieveAirportLocation(final String airportCode) throws ExecutionException, InterruptedException {
        Location location;

        /*location = restTemplate.getForObject(getAirportLocationUriBuilder(airportCode).toUriString(), Location.class);*/

        final Future<Location> locationFuture = executorService
                .submit(new LocationTask(getAirportLocationUriBuilder(airportCode), restTemplate));

        location = locationFuture.get();

        /*log.debug("location: {}", location);*/
        LOGGER.debug("location: {}", location);

        return location;
    }

    @Override
    public Fare retrieveFare(final String origin, final String destination) throws ExecutionException, InterruptedException {
        Fare fare;

        final Future<Location> originFuture = executorService
                .submit(new LocationTask(getAirportLocationUriBuilder(origin), restTemplate));
        final Future<Location> deLocationFuture = executorService
                .submit(new LocationTask(getAirportLocationUriBuilder(destination), restTemplate));
        final Future<Fare> fareFuture = executorService.submit(new FareTask(getFareUriBuilder(origin, destination), restTemplate));

        fare = new Fare();
        fare.setOrigin(originFuture.get().getDescription());
        fare.setDestination(deLocationFuture.get().getDescription());
        fare.setAmount(fareFuture.get().getAmount());
        fare.setCurrency(fareFuture.get().getCurrency());

        /*log.debug("fare: {}", fare);*/
        LOGGER.debug("fare: {}", fare);

        return fare;
    }

    private UriComponents getAirportLocationUriBuilder(final String airportCode) {
        final UriComponentsBuilder airportLocationUriBuilder = getURIBuilder("/airports/{code}");

        final Map<String, String> airportLocationUriVariableValues = new HashMap<>();
        airportLocationUriVariableValues.put("code", airportCode);

        return airportLocationUriBuilder.buildAndExpand(airportLocationUriVariableValues);
    }

    private UriComponents getFareUriBuilder(final String origin, final String destination) {
        final UriComponentsBuilder fareUriBuilder = getURIBuilder("/fares/{origin_code}/{destination_code}");

        final Map<String, String> fareUriVariableValues = new HashMap<>();
        fareUriVariableValues.put("origin_code", origin);
        fareUriVariableValues.put("destination_code", destination);

        return fareUriBuilder.buildAndExpand(fareUriVariableValues);
    }

    private UriComponentsBuilder getURIBuilder(final String uri) {
        final UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(serviceUrl + uri);
        builder.queryParam("grant_type", "client_credentials");
        builder.queryParam("client_id", "travel-api-client");
        builder.queryParam("client_secret", "psw");
        builder.queryParam("access_token", accessToken);

        return builder;
    }

}
