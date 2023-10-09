package com.ta2.probechallenge.planet.controller;

import com.ta2.probechallenge.planet.dto.in.PlanetCreateDto;
import com.ta2.probechallenge.planet.dto.out.ListPlanetDto;
import com.ta2.probechallenge.planet.dto.out.PlanetDto;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "create a planet")
    @PostMapping
    public ResponseEntity<PlanetDto> create(@RequestBody @Valid PlanetCreateDto planetCreateDto);
    @Operation(summary = "list planets")
    @GetMapping
    public ResponseEntity<List<ListPlanetDto>> findAll();

    @Operation(summary = "update information of a planet")
    @PatchMapping("/{id}")
    public ResponseEntity<PlanetDto> update(
            @PathVariable
            @Parameter(name = "id", example = "40b3ebd5-2475-42ec-8e50-38384ae7d80b")
            UUID id,
            @Valid @RequestBody PlanetCreateDto planetCreateDto
    );

    @Operation(summary = "get information of a planet")
    @GetMapping("/{id}")
    public ResponseEntity<PlanetDto> get(@PathVariable
                                         @Parameter(name = "id", example = "40b3ebd5-2475-42ec-8e50-38384ae7d80b")
                                         UUID id);
    @Operation(summary = "delete a planet")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable
                                       @Parameter(name = "id", example = "40b3ebd5-2475-42ec-8e50-38384ae7d80b")
                                       UUID id);

}
