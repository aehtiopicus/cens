package com.aehtiopicus.cens.repository.cens;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.Curso;
import com.aehtiopicus.cens.domain.entities.Profesor;

@Repository
public interface AsignaturCensRepository extends JpaRepository<Asignatura, Long>, JpaSpecificationExecutor<Asignatura>{



	public Asignatura findByNombreAndModalidadAndCurso(String nombre,
			String modalidad, Curso curso);

	@Query("SELECT a FROM Asignatura a WHERE a.profesor = ?1 OR a.profesorSuplente = ?1 AND a.vigente = true")
	public List<Asignatura> findAsignaturaByProfesor(Profesor profesor);

	@Modifying
	@Query("UPDATE Asignatura a SET a.profesor = (CASE WHEN a.profesor = :profe THEN  null END), a.profesorSuplente = (CASE WHEN a.profesorSuplente = :profe THEN  null END) WHERE a.profesor = :profe or a.profesorSuplente = :profe")
	public int removeProfesor(@Param("profe")Profesor profesor);

}
