package com.ta2.probechallenge.repository;


import com.ta2.probechallenge.entity.ProbeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProbeRepository extends JpaRepository<ProbeEntity, Long> {
}
