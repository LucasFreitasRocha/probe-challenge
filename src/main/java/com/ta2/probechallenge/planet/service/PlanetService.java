package com.ta2.probechallenge.planet.service;

import com.ta2.probechallenge.planet.repository.PlanetRespositoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanetService {

    @Autowired
    private PlanetRespositoryAdapter adapter;



}
