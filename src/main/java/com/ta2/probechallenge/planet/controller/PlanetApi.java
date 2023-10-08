package com.ta2.probechallenge.planet.controller;

import com.ta2.probechallenge.planet.dto.in.PlanetCreateDto;
import com.ta2.probechallenge.planet.dto.out.ListPlanetDto;
import com.ta2.probechallenge.planet.dto.out.PlanetDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/planet/v1")
@Tag(name = "planet")
public interface PlanetApi {

    @PostMapping
    public ResponseEntity<PlanetDto> create(@RequestBody @Valid PlanetCreateDto planetCreateDto);

    @GetMapping
    public ResponseEntity<List<ListPlanetDto>> findAll();

    @PatchMapping("/{id}")
    public ResponseEntity<PlanetDto> update(
            @PathVariable
            @Parameter(name = "id", example = "40b3ebd5-2475-42ec-8e50-38384ae7d80b")
            UUID id,
            @Valid @RequestBody PlanetCreateDto planetCreateDto
    );

    @GetMapping("/{id}")
    public ResponseEntity<PlanetDto> get(@PathVariable
                                         @Parameter(name = "id", example = "40b3ebd5-2475-42ec-8e50-38384ae7d80b")
                                         UUID id);

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable
                                       @Parameter(name = "id", example = "40b3ebd5-2475-42ec-8e50-38384ae7d80b")
                                       UUID id);

}
