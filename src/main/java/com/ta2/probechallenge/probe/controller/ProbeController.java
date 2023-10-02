package com.ta2.probechallenge.probe.controller;

import com.ta2.probechallenge.probe.domain.ProbeDomain;
import com.ta2.probechallenge.probe.dto.in.CommandDto;
import com.ta2.probechallenge.probe.service.ProbeService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/probe")
public class ProbeController {

    @Autowired private ProbeService service;

    @PostMapping("/{id}")
    public ResponseEntity<ProbeDomain> moveProbe(
            @PathVariable
            @ApiParam(name = "id", value = "Id of Probe", example = "1") Long id,
            @RequestBody CommandDto commandDTO){
        return ResponseEntity.ok(service.instruction(id,commandDTO));
    }
}
