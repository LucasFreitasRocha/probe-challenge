package com.ta2.probechallenge.probe.controller;

import com.ta2.probechallenge.probe.domain.ProbeDomain;
import com.ta2.probechallenge.probe.dto.in.CommandDto;
import com.ta2.probechallenge.probe.dto.in.CreateProbeDto;
import com.ta2.probechallenge.probe.service.ProbeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/probe")
public class ProbeController {

    @Autowired
    private ProbeService service;

    @PostMapping("/{id}")
    @Operation
    public ResponseEntity<ProbeDomain> moveProbe(
            @PathVariable
            @Parameter(name = "id", example = "1") Long id,
            @RequestBody CommandDto commandDTO) {
        return ResponseEntity.ok(service.instruction(id, commandDTO));
    }


    @PostMapping
    public ResponseEntity<ProbeDomain> create(@RequestBody CreateProbeDto createProbeDto) {
        ProbeDomain domain = service.create(createProbeDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(domain.getId()).toUri();
        return ResponseEntity.created(uri).body(domain);
    }
}
