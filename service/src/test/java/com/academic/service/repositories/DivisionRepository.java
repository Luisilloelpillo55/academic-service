package com.academic.service.repositories;

import com.academic.service.entities.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {
    boolean existsByClave(String clave);
}