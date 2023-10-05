package com.ta2.probechallenge.probe.service;

import com.ta2.probechallenge.probe.domain.ProbeDomain;
import com.ta2.probechallenge.probe.dto.in.CommandDto;
import com.ta2.probechallenge.probe.dto.in.NameProbeDto;
import com.ta2.probechallenge.probe.dto.out.ProbeDto;
import com.ta2.probechallenge.probe.repository.ProbeRepositoryAdapter;
import com.ta2.probechallenge.probe.validation.PobreValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ProbeService {
    @Autowired
    private ProbeRepositoryAdapter repository;
    @Autowired
    private PobreValidation validation;

    private final Map<String, String> spinLeft = Map.of(
            "N", "W",
            "E", "N",
            "S", "E",
            "W", "S"
    );
    private final Map<String, String> spinRight = Map.of(
            "N", "E",
            "E", "S",
            "S", "W",
            "W", "N"
    );

    public ProbeDto create(NameProbeDto nameProbeDto) {
        return ProbeDto.from(repository.save(ProbeDomain
                .builder()
                .name(nameProbeDto.name())
                .code(validationCode(nameProbeDto.name()))
                .x(0)
                .y(0)
                .position("N")
                .build()));
    }

    private String genereteCode(String name) {
        name = name.trim();
        StringBuilder codeBuilder = new StringBuilder();
        Arrays.stream(name.split(" ")).toList().forEach(word -> codeBuilder.append(word.toUpperCase().charAt(0)));
        return codeBuilder.toString();
    }

    public ProbeDto instruction(Long id, CommandDto commandDTO) {
        List<String> commands = Arrays.asList(commandDTO.command().split(""));
        ProbeDomain probeDomain = repository.find(id);
        commands.forEach(command -> {
            if (command.equals("M")) {
                move(probeDomain);
            } else {
                probeDomain.setPosition(
                        (command.equalsIgnoreCase("L") ?
                                spinLeft.getOrDefault(probeDomain.getPosition(), "W") :
                                spinRight.getOrDefault(probeDomain.getPosition(), "E")
                        ));
            }
        });

        validation.position(probeDomain);
        return ProbeDto.from(repository.save(probeDomain));
    }


    private void move(ProbeDomain probeDomain) {
        switch (probeDomain.getPosition()) {
            case "N" -> probeDomain.setY(probeDomain.getY() + 1);
            case "E" -> probeDomain.setX(probeDomain.getX() + 1);
            case "S" -> probeDomain.setY(probeDomain.getY() - 1);
            case "W" -> probeDomain.setX(probeDomain.getX() - 1);
        }
    }

    private String validationCode(String name) {
        String code = genereteCode(name);
        validation.CanCreateWithThisCode(code);
        return code;
    }

    public ProbeDto updateName(Long id, NameProbeDto nameProbeDto) {
        ProbeDomain domain = repository.find(id);
        domain.setCode(validationCode(nameProbeDto.name()));
        domain.setName(nameProbeDto.name());
        return ProbeDto.from(repository.save(domain));
    }

    public ProbeDto findByid(Long id) {
        return ProbeDto.from(repository.find(id));
    }

    public void delete(Long id) {
        repository.delete(repository.find(id));
    }

    public List<ProbeDomain> findAll() {
        return repository.findAll();
    }
}
