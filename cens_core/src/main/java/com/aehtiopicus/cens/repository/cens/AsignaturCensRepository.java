package com.aehtiopicus.cens.repository.cens;

import java.math.BigInteger;
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
	
	@Query(nativeQuery=true, value="SELECT count(a.*) FROM cens_asignatura a WHERE a.profesor_id = ?1 OR a.profesorsuplente_id = ?1 AND a.vigente = true")
	public BigInteger countAsignaturaByProfesor(Long idProfesor);

	@Modifying
	@Query(nativeQuery=true, value="UPDATE cens_asignatura  SET profesor_id = CASE WHEN profesor_id = :profe THEN null ELSE profesor_id END, profesorsuplente_id = CASE WHEN profesorsuplente_id = :profe THEN null ELSE profesorsuplente_id END WHERE vigente = true ")
	public int removeProfesor(@Param("profe")Long profesorID);

}
