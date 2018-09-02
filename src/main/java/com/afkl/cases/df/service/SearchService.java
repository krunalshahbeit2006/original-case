package com.afkl.cases.df.service;

import com.afkl.cases.df.model.Fare;
import com.afkl.cases.df.model.Location;

import java.util.concurrent.ExecutionException;

public interface SearchService {

    Location retrieveAirportLocation(final String airportCode) throws ExecutionException, InterruptedException;

    Fare retrieveFare(final String origin, final String destination) throws ExecutionException, InterruptedException;

}