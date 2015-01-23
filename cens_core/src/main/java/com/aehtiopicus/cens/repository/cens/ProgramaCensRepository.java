package com.aehtiopicus.cens.repository.cens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.Programa;

@Repository
public interface ProgramaCensRepository extends JpaRepository<Programa,Long>{

	public Programa findByAsignatura(Asignatura asignatura);

}
