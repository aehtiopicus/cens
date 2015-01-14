package com.aehtiopicus.cens.repository.cens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.Curso;

@Repository
public interface CursoCensRepository extends JpaRepository<Curso, Long>, JpaSpecificationExecutor<Curso> {

	public Curso findByYearCursoAndNombre(int year, String nombre);

}
