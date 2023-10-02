package com.ta2.probechallenge.service;

import com.ta2.probechallenge.dto.in.CommandDTO;
import com.ta2.probechallenge.entity.ProbeEntity;
import com.ta2.probechallenge.repository.ProbeRepository;

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
    private ProbeRepository repository;

    private Long id = Long.valueOf(1) ;

    private final String RESPONSETHROW = "No row with the given identifier exists:" +
            " [Sonda não encontrada com esse id: 1#1]";




    @Test
    void findOk() {
        ProbeEntity expect = getProbe();
        when(repository.findById(id)).thenReturn(Optional.of(expect)) ;
        ProbeEntity response = service.find(id);
        assertEquals(expect.getPosition(),response.getPosition() );
        assertEquals(expect.getX(),response.getX() );
        assertEquals(expect.getY(), response.getY());
        assertEquals(expect.getName(), response.getName());
        assertEquals(expect.getId(), response.getId());
    }

    @Test
    void findThrow() {
        when(repository.findById(id)).thenReturn(Optional.empty()) ;
       try{
           service.find(id);
       }catch (Exception e){
           assertEquals(RESPONSETHROW, e.getMessage());
       }
    }

    @Test
    void moveWithSingleCommandWhitProbe1(){
        when(repository.findById(id)).thenReturn(Optional.of(getProbe())) ;
        ProbeEntity response = service.instruction(id,getCommandDTO("M"));
        assertEquals(3, response.getY());

    }

    @Test
    void command1Probe1(){
        when(repository.findById(id)).thenReturn(Optional.of(getProbe())) ;
        ProbeEntity response = service.instruction(id,getCommandDTO("LMLMLMLMM"));
        assertEquals(1, response.getX());
        assertEquals(3, response.getY());
        assertEquals("N", response.getPosition());
    }

    public CommandDTO getCommandDTO(String command){
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.setCommand(command);
        return  commandDTO;
    }

    private ProbeEntity getProbe() {
        ProbeEntity probeEntity = new ProbeEntity();
        probeEntity.setId(id);
        probeEntity.setPosition("N");
        probeEntity.setX(1);
        probeEntity.setY(2);
        probeEntity.setName("mars rover");
        return probeEntity;
    }

    @Test
    void instruction() {

    }
}