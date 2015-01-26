package com.aehtiopicus.cens.repository.cens;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.Programa;

@Repository
public interface ProgramaCensRepository extends JpaRepository<Programa,Long>{

	public Programa findByAsignatura(Asignatura asignatura);
	
	@Query("SELECT p FROM Programa p INNER JOIN p.asignatura a WHERE (a.profesor.id = :profesorId OR a.profesorSuplente.id = :profesorId) AND a.vigente = true")
	public List<Programa> findProgramaByProfesor(@Param("profesorId")Long profesorId);

}
