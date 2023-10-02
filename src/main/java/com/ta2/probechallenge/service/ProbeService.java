package com.ta2.probechallenge.service;

import com.ta2.probechallenge.dto.in.CommandDTO;
import com.ta2.probechallenge.entity.ProbeEntity;
import com.ta2.probechallenge.repository.ProbeRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProbeService {
    @Autowired
    private ProbeRepository repository;

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

    public ProbeEntity find(Long id) {
        Optional<ProbeEntity> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(id,
                "Sonda não encontrada com esse id: " + id));
    }


    public ProbeEntity instruction(Long id, CommandDTO commandDTO) {
        List<String> commands = Arrays.asList(commandDTO.getCommand().split(""));
        ProbeEntity probeEntity = find(id);
        commands.forEach(command -> {
            if (command.equals("M")) {
                move(probeEntity);
            } else {
                probeEntity.setPosition(
                        (command.equalsIgnoreCase("L") ?
                                spinLeft.get(probeEntity.getPosition()) :
                                spinRight.get(probeEntity.getPosition())
                        ));
            }
        });
        return probeEntity;
    }


    private void move(ProbeEntity probeEntity) {
        switch (probeEntity.getPosition()) {
            case "N" -> probeEntity.setY(probeEntity.getY() + 1);
            case "E" -> probeEntity.setX(probeEntity.getX() + 1);
            case "S" -> probeEntity.setY(probeEntity.getY() - 1);
            case "W" -> probeEntity.setX(probeEntity.getX() - 1);
        }
    }
}
