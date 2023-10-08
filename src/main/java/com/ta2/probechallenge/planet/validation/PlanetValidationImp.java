package com.ta2.probechallenge.planet.validation;

import com.ta2.probechallenge.enums.ResourceName;
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
    public void validationUpdateUniqueName(String name, UUID id) {
        Optional<PlanetDomain> optional = respositoryAdapter.findByName(name);
        if (Objects.isNull(id) && optional.isPresent() ||
                !Objects.isNull(id) && optional.isPresent() && !optional.get().getId().equals(id)) {
            throw CustomException.buildBy(CodeExceptionEnum.CREATION_OR_UPDATE_UNAVAILABLE, ResourceName.PLANET.getValue(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public PlanetDomain canDelete(UUID id) {
        PlanetDomain domain = respositoryAdapter.find(id);
        if(domain.getProbes().size() > 0) throw CustomException.buildBy(CodeExceptionEnum.DELETE_UNAVAILABLE, ResourceName.PLANET.getValue(), HttpStatus.BAD_REQUEST);
        return domain;
    }
}
