package com.ta2.probechallenge.probe.controller;

import com.ta2.probechallenge.probe.domain.ProbeDomain;
import com.ta2.probechallenge.probe.dto.in.CommandDto;
import com.ta2.probechallenge.probe.dto.in.NameProbeDto;
import com.ta2.probechallenge.probe.dto.out.ProbeDto;
import com.ta2.probechallenge.probe.service.ProbeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
public class ProbeController implements ProbeApi {

    @Autowired
    private ProbeService service;

    @Override
    public ResponseEntity<ProbeDto> moveProbe(Long id, CommandDto commandDTO) {
        return ResponseEntity.ok(service.instruction(id, commandDTO));
    }

    @Override
    public ResponseEntity<ProbeDto> create(NameProbeDto nameProbeDto) {
        ProbeDto dto = service.create(nameProbeDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Override
    public ResponseEntity<ProbeDto> getProbe(Long id) {
        return ResponseEntity.ok(service.findByid(id));
    }

    @Override
    public ResponseEntity<Void> updateName(Long id, NameProbeDto nameProbeDto) {
        service.updateName(id, nameProbeDto);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<ProbeDomain>> index() {
        return ResponseEntity.ok(service.findAll());
    }
}
