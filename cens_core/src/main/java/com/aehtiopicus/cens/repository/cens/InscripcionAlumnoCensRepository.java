package com.aehtiopicus.cens.repository.cens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.InscripcionAlumnos;

@Repository
public interface InscripcionAlumnoCensRepository extends JpaRepository<InscripcionAlumnos,Long>,JpaSpecificationExecutor<InscripcionAlumnos>{

}
