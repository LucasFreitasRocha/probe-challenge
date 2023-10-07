package com.ta2.probechallenge.planet.validation;

import com.ta2.probechallenge.exception.CodeExceptionEnum;
import com.ta2.probechallenge.exception.CustomException;
import com.ta2.probechallenge.planet.domain.PlanetDomain;
import com.ta2.probechallenge.planet.repository.PlanetRespositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PlanetValidationImp implements PlanetValidation {
    private final PlanetRespositoryAdapter respositoryAdapter;

    @Override
    public void ValidationIfHaveAPlanetWithThisName(String name, UUID id) {
        Optional<PlanetDomain> optional = respositoryAdapter.findByName(name);
        if (Objects.isNull(id) && optional.isPresent() ||
                !Objects.isNull(id) && optional.isPresent() && !optional.get().getId().equals(id)) {
            throw CustomException.buildBy(CodeExceptionEnum.CREATION_UNAVAILABLE, HttpStatus.BAD_REQUEST);
        }

        if (respositoryAdapter.findByName(name).isPresent()) {

        }
    }
}
