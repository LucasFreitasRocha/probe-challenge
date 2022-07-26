package com.ta2.probechallenge.service;

import com.ta2.probechallenge.dto.in.CommandDTO;
import com.ta2.probechallenge.model.Probe;
import com.ta2.probechallenge.repository.ProbeRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProbeService {
    @Autowired private ProbeRepository repository;

    public  Probe find(Long id){
        Optional<Probe> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(id,
                "Sonda não encontrada com esse id: " + id));
    }


    public Probe instruction(Long id, CommandDTO commandDTO) {
        List<String> commands = Arrays.asList( commandDTO.getCommand().split(""));
        Probe probe = find(id);

        commands.forEach(command -> {
            if(command.equals("M")){
                move(probe);
            }else{
                spin(probe, command);
            }
        });
        return probe;
    }

    private void spin(Probe probe, String command) {
        String newPosition = "";
        if(command.equals("L")){
            if(probe.getPosition().equals("N")) newPosition = "W";
            if(probe.getPosition().equals("E")) newPosition = "N";
            if(probe.getPosition().equals("S")) newPosition = "E";
            if(probe.getPosition().equals("W")) newPosition = "S";
        }else{
            if(probe.getPosition().equals("N")) newPosition = "E";
            if(probe.getPosition().equals("E")) newPosition = "S";
            if(probe.getPosition().equals("S")) newPosition = "W";
            if(probe.getPosition().equals("W")) newPosition = "N";
        }
        probe.setPosition(newPosition);
    }

    private void move(Probe probe) {
        if(probe.getPosition().equals("N")) probe.setY(probe.getY() + 1);
        if(probe.getPosition().equals("E")) probe.setX(probe.getX() + 1);
        if(probe.getPosition().equals("S")) probe.setY(probe.getY() - 1);
        if(probe.getPosition().equals("W")) probe.setX(probe.getX() - 1);
    }
}
