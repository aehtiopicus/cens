package com.aehtiopicus.cens.repository.cens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Profesor;

@Repository
public interface ProfesorCensRepository extends JpaRepository<Profesor,Long>{

	public Profesor findOneByMiembroCens(MiembroCens usuario);
	
	@Modifying
	@Query("UPDATE Profesor p SET p.baja = true WHERE p.miembroCens = : miembroCens")
	public void markAsesorAsDisable(@Param("miembroCens")MiembroCens miembroCens);
}
