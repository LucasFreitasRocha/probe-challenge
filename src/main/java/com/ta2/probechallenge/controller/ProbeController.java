package com.ta2.probechallenge.controller;

import com.ta2.probechallenge.domain.ProbeDomain;
import com.ta2.probechallenge.dto.in.CommandDTO;
import com.ta2.probechallenge.entity.ProbeEntity;
import com.ta2.probechallenge.service.ProbeService;
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
            @RequestBody CommandDTO commandDTO){
        return ResponseEntity.ok(service.instruction(id,commandDTO));
    }
}
