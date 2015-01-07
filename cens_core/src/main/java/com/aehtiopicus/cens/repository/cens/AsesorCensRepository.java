package com.aehtiopicus.cens.repository.cens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.Asesor;
import com.aehtiopicus.cens.domain.entities.MiembroCens;

@Repository
public interface AsesorCensRepository extends JpaRepository<Asesor, Long>{

	public Asesor findOneByMiembroCens(MiembroCens usuario);

	@Modifying
	@Query("UPDATE Asesor a SET a.baja = true WHERE a.miembroCens = : miembroCens")
	public void markAsesorAsDisable(@Param("miembroCens")MiembroCens miembroCens);

}
