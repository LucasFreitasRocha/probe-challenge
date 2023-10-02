package com.ta2.probechallenge.service;

import com.ta2.probechallenge.domain.ProbeDomain;
import com.ta2.probechallenge.dto.in.CommandDTO;
import com.ta2.probechallenge.entity.ProbeEntity;
import com.ta2.probechallenge.repository.ProbeRepositoryAdapter;
import com.ta2.probechallenge.repository.ProbeRepositorySql;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

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
        ProbeDomain response = service.instruction(id,getCommandDTO("M"));
        assertEquals(3, response.getY());

    }

    @Test
    void command1Probe1(){
        when(repository.find(id)).thenReturn(getProbe()) ;
        ProbeDomain response = service.instruction(id,getCommandDTO("LMLMLMLMM"));
        assertEquals(1, response.getX());
        assertEquals(3, response.getY());
        assertEquals("N", response.getPosition());
    }

    public CommandDTO getCommandDTO(String command){
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.setCommand(command);
        return  commandDTO;
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