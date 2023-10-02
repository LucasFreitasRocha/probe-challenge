package com.ta2.probechallenge.repository;

import com.ta2.probechallenge.domain.ProbeDomain;
import com.ta2.probechallenge.entity.ProbeEntity;

public interface ProbeRepositoryGateway {
    public ProbeDomain find(Long id);


}
