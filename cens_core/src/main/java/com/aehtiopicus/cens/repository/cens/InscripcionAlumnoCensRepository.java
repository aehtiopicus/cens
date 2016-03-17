package com.aehtiopicus.cens.repository.cens;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.InscripcionAlumnos;

@Repository
public interface InscripcionAlumnoCensRepository extends JpaRepository<InscripcionAlumnos,Long>,JpaSpecificationExecutor<InscripcionAlumnos>{

	@Query(nativeQuery=true,value ="DELETE FROM cens_inscripcion_alumno WHERE cens_alumno_id = ?2 AND cens_asignatura_id = ?1")
	@Modifying
	public int eliminarInscripcion(Long asignaturaId, Long alumnoId);

	public List<InscripcionAlumnos> findByAlumnoIdEqualsAndAsignaturaVigenteEquals(Long alumnoId,boolean vigente);
	
	@Query(nativeQuery=true,value ="DELETE FROM cens_inscripcion_alumno WHERE cens_asignatura_id = ?1")
	@Modifying
	public int eliminarInscripcion(Long asignaturaId);

}
