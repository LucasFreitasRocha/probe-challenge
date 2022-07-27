package com.ta2.probechallenge.controller;

import com.ta2.probechallenge.dto.in.CommandDTO;
import com.ta2.probechallenge.model.Probe;
import com.ta2.probechallenge.service.ProbeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/probe")
public class ProbeController {

    @Autowired private ProbeService service;

    @PostMapping("/{id}")
    public ResponseEntity<Probe> moveProbe(
            @PathVariable
            @ApiParam(name = "id", value = "Id of Probe", example = "1") Long id,
            @RequestBody CommandDTO commandDTO){
        return ResponseEntity.ok(service.instruction(id,commandDTO));
    }
}
