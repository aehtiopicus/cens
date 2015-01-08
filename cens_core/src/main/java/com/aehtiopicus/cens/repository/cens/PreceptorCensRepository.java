package com.aehtiopicus.cens.repository.cens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Preceptor;

@Repository
public interface PreceptorCensRepository extends JpaRepository<Preceptor,Long>{

	public Preceptor findOneByMiembroCens(MiembroCens usuario);
	
	@Modifying
	@Query("UPDATE Preceptor p SET p.baja = true WHERE p.miembroCens = ?1")
	public void markAsesorAsDisable(MiembroCens miembroCens);
}
