package com.aehtiopicus.cens.repository.cens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.Asesor;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Usuarios;

@Repository
public interface AsesorCensRepository extends JpaRepository<Asesor, Long>{

	public Asesor findOneByMiembroCens(MiembroCens usuario);

}
