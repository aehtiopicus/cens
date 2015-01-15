package com.aehtiopicus.cens.repository.cens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.Curso;

@Repository
public interface AsignaturCensRepository extends JpaRepository<Asignatura, Long>, JpaSpecificationExecutor<Asignatura>{



	public Asignatura findByNombreAndModalidadAndCurso(String nombre,
			String modalidad, Curso curso);

}
