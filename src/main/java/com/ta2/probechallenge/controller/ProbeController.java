package com.ta2.probechallenge.controller;

import com.ta2.probechallenge.dto.in.CommandDTO;
import com.ta2.probechallenge.model.Probe;
import com.ta2.probechallenge.service.ProbeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/probe")
public class ProbeController {

    @Autowired private ProbeService service;

    @PostMapping("/{id}")
    public ResponseEntity<Probe> moveProbe(@PathVariable Long id, @RequestBody CommandDTO commandDTO){
        System.out.println("Iniciou");
        return ResponseEntity.ok(service.instruction(id,commandDTO));
    }
}
