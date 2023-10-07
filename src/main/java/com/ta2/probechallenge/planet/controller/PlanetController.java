package com.ta2.probechallenge.planet.controller;

import com.ta2.probechallenge.planet.dto.in.PlanetCreateDto;
import com.ta2.probechallenge.planet.dto.out.ListPlanetDto;
import com.ta2.probechallenge.planet.dto.out.PlanetDto;
import com.ta2.probechallenge.planet.service.PlanetService;
import com.ta2.probechallenge.probe.dto.out.ProbeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
@RestController
public class PlanetController implements PlanetApi{
    @Autowired
    PlanetService service;
    @Override
    public ResponseEntity<PlanetDto> create(PlanetCreateDto planetCreateDto) {
        PlanetDto dto = service.create(planetCreateDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Override
    public ResponseEntity<List<ListPlanetDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Override
    public ResponseEntity<PlanetDto> update(UUID id, PlanetCreateDto planetCreateDto) {
        return ResponseEntity.ok(service.update(id, planetCreateDto));
    }

    @Override
    public ResponseEntity<PlanetDto> get(UUID id) {
        return ResponseEntity.ok(service.find(id));
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        service.delte(id);
        return ResponseEntity.noContent().build();
    }
}
