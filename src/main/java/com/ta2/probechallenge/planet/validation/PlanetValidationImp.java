package com.ta2.probechallenge.planet.validation;

import com.ta2.probechallenge.enums.ResourceName;
import com.ta2.probechallenge.exception.CodeExceptionEnum;
import com.ta2.probechallenge.exception.CustomException;
import com.ta2.probechallenge.planet.domain.PlanetDomain;
import com.ta2.probechallenge.planet.repository.PlanetRespositoryAdapter;
import com.ta2.probechallenge.probe.domain.ProbeDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
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
            throw CustomException.buildBy(CodeExceptionEnum.CREATION_OR_UPDATE_UNAVAILABLE,
                    ResourceName.PLANET.getValue(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public PlanetDomain canUseThisPlanet(UUID id) {
        PlanetDomain domain = respositoryAdapter.find(id);
        if (domain.getProbes().size() >= domain.getMaxProbesIn())
            throw CustomException.buildBy(CodeExceptionEnum.MAX_PROBES_ON_PLANET,
                    ResourceName.PLANET.getValue(), HttpStatus.BAD_REQUEST);
        return domain;
    }

    @Override
    public PlanetDomain canDelete(UUID id) {
        PlanetDomain domain = respositoryAdapter.find(id);
        if (domain.getProbes().size() > 0)
            throw CustomException.buildBy(CodeExceptionEnum.DELETE_UNAVAILABLE, ResourceName.PLANET.getValue(), HttpStatus.BAD_REQUEST);
        return domain;
    }

    @Override
    public void validationPositionOfProbeOn(ProbeDomain probeDomain) {
        PlanetDomain planetDomain = respositoryAdapter.find(probeDomain.getPlanet().getId());
        if (probeDomain.getX().equals(0) && probeDomain.getY().equals(0))
            throw CustomException.buildBy(CodeExceptionEnum.INVALID_POSITION,
                    "The [0,0] position is reserved for the landing of the probes", HttpStatus.BAD_REQUEST);
        List<ProbeDomain> find = planetDomain.getProbes().stream().filter(filterProbe ->
                filterProbe.getY().equals(probeDomain.getY())
                        && filterProbe.getX().equals(probeDomain.getX())
                        && !filterProbe.getId().equals(probeDomain.getId())).toList();
        if (find.size() > 0) throw CustomException.buildBy(CodeExceptionEnum.INVALID_POSITION,
                "Already has a probe in this position", HttpStatus.BAD_REQUEST);

        if (probeDomain.getX() > Math.abs(planetDomain.getArea() )
                || probeDomain.getY() > Math.abs(planetDomain.getArea())) {
            throw CustomException.buildBy(CodeExceptionEnum.INVALID_POSITION,
                    "the probes try to pass area of planet", HttpStatus.BAD_REQUEST);
        }

    }
}
