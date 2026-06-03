package com.academic.service.repositories;

import com.academic.service.entities.ProgramaEducativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramaEducativoRepository extends JpaRepository<ProgramaEducativo, Long> {
    List<ProgramaEducativo> findByDivisionId(Long divisionId);
}
