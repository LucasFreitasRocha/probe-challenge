package com.ta2.probechallenge.probe.validation;

import com.ta2.probechallenge.exception.CustomException;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProbeValidationImpTest {
    @InjectMocks
    ProbeValidationImp probeValidationImp;
    @Test
    void command() {
        try {
            probeValidationImp.command("rlmhs");
        }catch (Exception e){
            assertTrue(e instanceof CustomException);
        }

    }
}