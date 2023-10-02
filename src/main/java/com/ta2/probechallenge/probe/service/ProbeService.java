package com.ta2.probechallenge.probe.service;

import com.ta2.probechallenge.probe.domain.ProbeDomain;
import com.ta2.probechallenge.probe.dto.in.CommandDto;
import com.ta2.probechallenge.probe.repository.ProbeRepositoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ProbeService {
    @Autowired
    private ProbeRepositoryAdapter repository;

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


    public ProbeDomain instruction(Long id, CommandDto commandDTO) {
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
        return probeDomain;
    }


    private void move(ProbeDomain probeDomain) {
        switch (probeDomain.getPosition()) {
            case "N" -> probeDomain.setY(probeDomain.getY() + 1);
            case "E" -> probeDomain.setX(probeDomain.getX() + 1);
            case "S" -> probeDomain.setY(probeDomain.getY() - 1);
            case "W" -> probeDomain.setX(probeDomain.getX() - 1);
        }
    }
}
