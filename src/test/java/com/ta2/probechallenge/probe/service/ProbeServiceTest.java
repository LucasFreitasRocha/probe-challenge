package com.ta2.probechallenge.probe.service;

import com.ta2.probechallenge.planet.domain.PlanetDomain;
import com.ta2.probechallenge.probe.domain.ProbeDomain;
import com.ta2.probechallenge.probe.dto.in.CommandDto;
import com.ta2.probechallenge.probe.repository.ProbeRepositoryAdapter;
import com.ta2.probechallenge.probe.validation.PobreValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProbeServiceTest {
    @InjectMocks
    private ProbeService service;


    @Mock
    private ProbeRepositoryAdapter repository;

    @Mock
    private PobreValidation validation;

    private Long id = Long.valueOf(1);


    @Test
    void moveWithSingleCommandWhitProbe1() {
        ProbeDomain domain = getProbe();
        when(repository.find(id)).thenReturn(getProbe());
        domain.setY(3);
        when(repository.save(domain)).thenReturn(domain);
        service.instruction(id, getCommandDTO("M"));
        verify(repository).save(domain);

    }

    @Test
    void command1Probe1() {
        ProbeDomain domain = getProbe();
        when(repository.find(id)).thenReturn(domain);
        domain.setX(1);
        domain.setY(3);
        when(repository.save(domain)).thenReturn(domain);
        service.instruction(id, getCommandDTO("LMLMLMLMM"));
        verify(repository).save(domain);
    }

    public CommandDto getCommandDTO(String command) {
        return new CommandDto(command);
    }

    private ProbeDomain getProbe() {
        PlanetDomain planetDomain =
                PlanetDomain.builder()
                        .id(UUID.fromString("40b3ebd5-2475-42ec-8e50-38384ae7d80b"))
                        .name("MARS")
                        .maxProbesIn(5)
                        .area(5)
                .build();
        return ProbeDomain.builder()
                .id(id)
                .x(1)
                .y(2)
                .name("mars rover")
                .position("N")
                .planet(planetDomain)
                .build();
    }


}