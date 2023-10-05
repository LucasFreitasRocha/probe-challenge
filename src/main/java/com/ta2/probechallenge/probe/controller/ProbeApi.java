package com.ta2.probechallenge.probe.controller;

import com.ta2.probechallenge.probe.domain.ProbeDomain;
import com.ta2.probechallenge.probe.dto.in.CommandDto;
import com.ta2.probechallenge.probe.dto.in.CreateProbeDto;
import com.ta2.probechallenge.probe.dto.in.NameProbeDto;
import com.ta2.probechallenge.probe.dto.out.ListProbeDto;
import com.ta2.probechallenge.probe.dto.out.ProbeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/probe/v1")
@Tag(name = "probe")
public interface ProbeApi {

    @PostMapping("/{id}")
    @Operation(summary = "Move probe in planet")
    public ResponseEntity<ProbeDto> moveProbe(
            @PathVariable
            @Parameter(name = "id", example = "1") Long id,
            @Valid
            @RequestBody CommandDto commandDTO);

    @Operation(summary = "create a probe")
    @PostMapping
    public ResponseEntity<ProbeDto> create(@Valid @RequestBody CreateProbeDto createProbeDto);

    @Operation(summary = "get a probe")
    @GetMapping("/{id}")
    public ResponseEntity<ProbeDto> getProbe(
            @PathVariable
            @Parameter(name = "id", example = "1") Long id
    );


    @PatchMapping("/{id}")
    @Operation(summary = "Update name probe")
    public ResponseEntity<ProbeDto> updateName(
            @PathVariable
            @Parameter(name = "id", example = "1") Long id,
            @RequestBody NameProbeDto nameProbeDto
    );


    @DeleteMapping("/{id}")
    @Operation(summary = "delete a probe")
    public ResponseEntity<Void> delete(
            @PathVariable
            @Parameter(name = "id", example = "1") Long id
    );

    @GetMapping
    @Operation(summary = "all probes available")
    public ResponseEntity<List<ListProbeDto>> index();
}
