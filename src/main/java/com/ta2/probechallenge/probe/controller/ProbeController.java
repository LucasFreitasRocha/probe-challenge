package com.ta2.probechallenge.probe.controller;

import com.ta2.probechallenge.probe.dto.in.CommandDto;
import com.ta2.probechallenge.probe.dto.in.CreateProbeDto;
import com.ta2.probechallenge.probe.dto.in.NameProbeDto;
import com.ta2.probechallenge.probe.dto.out.ListProbeDto;
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
    public ResponseEntity<ProbeDto> create(CreateProbeDto CreateProbeDto) {
        ProbeDto dto = service.create(CreateProbeDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Override
    public ResponseEntity<ProbeDto> getProbe(Long id) {
        return ResponseEntity.ok(service.findByid(id));
    }

    @Override
    public ResponseEntity<ProbeDto> updateName(Long id, NameProbeDto nameProbeDto) {
        return ResponseEntity.ok(service.updateName(id, nameProbeDto));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<ListProbeDto>> index() {
        return ResponseEntity.ok(service.findAll());
    }
}
