package com.ta2.probechallenge.probe.validation;

import com.ta2.probechallenge.enums.ResourceName;
import com.ta2.probechallenge.exception.CustomException;
import com.ta2.probechallenge.exception.ErroInformation;
import com.ta2.probechallenge.planet.domain.PlanetDomain;
import com.ta2.probechallenge.planet.validation.PlanetValidation;
import com.ta2.probechallenge.probe.domain.ProbeDomain;
import com.ta2.probechallenge.probe.dto.in.CreateProbeDto;
import com.ta2.probechallenge.probe.repository.ProbeRepositoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ta2.probechallenge.exception.CodeExceptionEnum.CREATION_OR_UPDATE_UNAVAILABLE;
import static com.ta2.probechallenge.exception.CodeExceptionEnum.INVALID_COMMAND;

@Component
public class ProbeValidationImp implements PobreValidation {

    @Autowired
    private ProbeRepositoryAdapter repositoryAdapter;
    @Autowired
    private PlanetValidation planetValidation;

    @Override
    public String canUseThisCode(String name) {
        String code = genereteCode(name);
        if (repositoryAdapter.findByCode(code).isPresent()) {
            throw CustomException.buildBy(CREATION_OR_UPDATE_UNAVAILABLE, ResourceName.PROBE.getValue(), HttpStatus.BAD_REQUEST);
        }
        return code;
    }


    @Override
    public void command(String command) {
        Pattern pattern = Pattern.compile("[^mrl]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            List<ErroInformation> erros = new ArrayList<>();
            erros.add(new ErroInformation(INVALID_COMMAND.code, INVALID_COMMAND.message));
            throw new CustomException(INVALID_COMMAND.message, HttpStatus.BAD_REQUEST, erros);
        }
    }

    @Override
    public ProbeDomain create(CreateProbeDto createProbeDto) {
        String code = this.canUseThisCode(createProbeDto.name());
        PlanetDomain planet = planetValidation.canUseThisPlanet(UUID.fromString(createProbeDto.idPlanet()));
        return ProbeDomain
                .builder()
                .name(createProbeDto.name())
                .code(code)
                .x(0)
                .y(0)
                .position("N")
                .planet(planet)
                .build();
    }

    @Override
    public void position(ProbeDomain probeDomain) {
        planetValidation.validationPositionOfProbeOn(probeDomain);
    }


    private String genereteCode(String name) {
        name = name.trim();
        StringBuilder codeBuilder = new StringBuilder();
        Arrays.stream(name.split(" ")).toList().forEach(word -> codeBuilder.append(word.toUpperCase().charAt(0)));
        return codeBuilder.toString();
    }
}
