package com.ta2.probechallenge.probe.repository;


import com.ta2.probechallenge.probe.entity.ProbeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProbeRepositorySql extends JpaRepository<ProbeEntity, Long> {
    Optional<ProbeEntity> findByCode(String code);
}
