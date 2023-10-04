package com.ta2.probechallenge.probe.service;

import com.ta2.probechallenge.probe.domain.ProbeDomain;
import com.ta2.probechallenge.probe.dto.in.CommandDto;
import com.ta2.probechallenge.probe.dto.out.ProbeDto;
import com.ta2.probechallenge.probe.repository.ProbeRepositoryAdapter;

import com.ta2.probechallenge.probe.service.ProbeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProbeServiceTest {
    @InjectMocks
    private ProbeService service;


    @Mock
    private ProbeRepositoryAdapter repository;

    private Long id = Long.valueOf(1) ;

    private final String RESPONSETHROW = "No row with the given identifier exists:" +
            " [Sonda não encontrada com esse id: 1#1]";








    @Test
    void moveWithSingleCommandWhitProbe1(){
        when(repository.find(id)).thenReturn(getProbe()) ;
        ProbeDto response = service.instruction(id,getCommandDTO("M"));
        assertEquals(3, response.y());

    }

    @Test
    void command1Probe1(){
        when(repository.find(id)).thenReturn(getProbe()) ;
        ProbeDto response = service.instruction(id,getCommandDTO("LMLMLMLMM"));
        assertEquals(1, response.x());
        assertEquals(3, response.y());
        assertEquals("N", response.position());
    }

    public CommandDto getCommandDTO(String command){
        return new CommandDto(command);
    }

    private ProbeDomain getProbe() {
        return ProbeDomain.builder()
                .id(id)
                .x(1)
                .y(2)
                .name("mars rover")
                .position("N")
                .build();
    }


}