package com.aehtiopicus.cens.repository.cens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.Asesor;
import com.aehtiopicus.cens.domain.entities.MiembroCens;

@Repository
public interface AsesorCensRepository extends JpaRepository<Asesor, Long>, JpaSpecificationExecutor<Asesor>{

	public Asesor findOneByMiembroCens(MiembroCens usuario);

	@Modifying
	@Query("UPDATE Asesor a SET a.baja = true WHERE a.miembroCens = ?1")
	public void markAsesorAsDisable(MiembroCens miembroCens);

}
