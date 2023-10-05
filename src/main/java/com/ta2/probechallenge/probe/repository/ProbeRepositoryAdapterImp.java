package com.ta2.probechallenge.probe.repository;

import com.ta2.probechallenge.exception.CustomException;
import com.ta2.probechallenge.exception.ErroInformation;
import com.ta2.probechallenge.probe.domain.ProbeDomain;
import com.ta2.probechallenge.probe.entity.ProbeEntity;
import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ProbeRepositoryAdapterImp implements ProbeRepositoryAdapter{

    private final ProbeRepositorySql probeRepositorySql;
    @Override
    public ProbeDomain find(Long id) {
        return ProbeDomain.from(probeRepositorySql.findById(id).orElseThrow(() -> {
            List<ErroInformation> erroInformations = new ArrayList<>();
            erroInformations.add(new ErroInformation(HttpStatus.NOT_FOUND.name(), "Not found probe"));
            return  new CustomException("Not found probe", HttpStatus.NOT_FOUND, erroInformations);
        }));
    }

    @Override
    public ProbeDomain save(ProbeDomain probeDomain) {
        return ProbeDomain.from(probeRepositorySql.save(ProbeEntity.from(probeDomain)));
    }

    @Override
    public void delete(ProbeDomain probeDomain) {
        probeRepositorySql.delete(ProbeEntity.from(probeDomain));
    }

    @Override
    public List<ProbeDomain> findAll() {
        return probeRepositorySql.findAll().stream().map(ProbeDomain::from).toList();
    }

    @Override
    public Optional<ProbeDomain> findByCode(String code) {
        Optional<ProbeEntity> optional = probeRepositorySql.findByCode(code);
        if(optional.isEmpty()) return Optional.empty();
        return Optional.of(ProbeDomain.from(optional.get()));
    }
}
