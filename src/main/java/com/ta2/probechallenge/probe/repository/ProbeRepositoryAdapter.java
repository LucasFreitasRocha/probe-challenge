package com.ta2.probechallenge.probe.repository;

import com.ta2.probechallenge.probe.domain.ProbeDomain;

import java.util.List;
import java.util.Optional;

public interface ProbeRepositoryAdapter {
    public ProbeDomain find(Long id);


    ProbeDomain save(ProbeDomain probeDomain);

    void delete(ProbeDomain probeDomain);

    List<ProbeDomain> findAll();

    Optional<ProbeDomain> findByCode(String code);
}
