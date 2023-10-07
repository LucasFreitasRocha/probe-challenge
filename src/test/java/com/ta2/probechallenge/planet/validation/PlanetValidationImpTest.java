package com.ta2.probechallenge.planet.validation;

import com.ta2.probechallenge.exception.CodeExceptionEnum;
import com.ta2.probechallenge.exception.CustomException;
import com.ta2.probechallenge.planet.domain.PlanetDomain;
import com.ta2.probechallenge.planet.repository.PlanetRespositoryAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlanetValidationImpTest {
    @InjectMocks
    PlanetValidationImp validation;

    @Mock
    PlanetRespositoryAdapter respositoryAdapter;

    private UUID ID = UUID.fromString("40b3ebd5-2475-42ec-8e50-38384ae7d80b");
    private String NAME = "Mars";

    //  should throw exception when pass a name that Already to create
    @Test
    public void shouldThrowCreate(){
        when(respositoryAdapter.findByName(NAME)).thenReturn(Optional.of(PlanetDomain.builder().id(ID).build()));
        try {
            validation.validationUpdateUniqueName(NAME, null);
        }catch (Exception e){
            assertTrue(e instanceof CustomException);
        }
    }
    //should Throw When Pass A Name that Already exist And Not is Same Planet
    @Test
    public void shouldThrowWithNamePassed(){
        when(respositoryAdapter.findByName(NAME)).thenReturn(Optional.of(PlanetDomain.builder().id(ID).build()));
        try {
            validation.validationUpdateUniqueName(NAME, UUID.randomUUID());
        }catch (Exception e){
            assertTrue(e instanceof CustomException);
        }
    }
}