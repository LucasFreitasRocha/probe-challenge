package com.ta2.probechallenge.repository;


import com.ta2.probechallenge.model.Probe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProbeRepository extends JpaRepository<Probe, Long> {
}
