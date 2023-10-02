package com.ta2.probechallenge.repository;

import com.ta2.probechallenge.domain.ProbeDomain;
import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProbeRepositoryAdapterImp implements ProbeRepositoryAdapter{

    private final ProbeRepositorySql probeRepositorySql;
    @Override
    public ProbeDomain find(Long id) {
        return ProbeDomain.from(probeRepositorySql.findById(id).orElseThrow(() -> new ObjectNotFoundException(id,
                "Sonda não encontrada com esse id: " + id)));
    }
}
