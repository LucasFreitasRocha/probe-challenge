package com.ta2.probechallenge.probe.repository;

import com.ta2.probechallenge.exception.CustomException;
import com.ta2.probechallenge.probe.entity.ProbeEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProbeRepositoryAdapterImpTest {

    @InjectMocks
    ProbeRepositoryAdapterImp probeRepositoryAdapterImp;

    @Mock
    ProbeRepositorySql probeRepositorySql;

    Long ID = Long.valueOf(1);
    @Test
    void findThrow() {
        when(probeRepositorySql.findById(ID)).thenReturn(Optional.empty());
        try {
            probeRepositoryAdapterImp.find(ID);
        }catch (Exception e){
            assertTrue(e instanceof CustomException);
            assertEquals(HttpStatus.NOT_FOUND, ((CustomException) e).getStatus());
            assertEquals("Not found probe",((CustomException) e).getMessage());
        }
    }

}