package com.aehtiopicus.cens.repository.cens;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.Contacto;

@Repository
public interface ContactoCensRepository extends JpaRepository<Contacto, Long>{

	@Query(value="SELECT c FROM Contacto c WHERE c.miembroCens.id = :idMiembro")
	public List<Contacto> findContactoByMiembro(@Param("idMiembro")Long miembroCensId);

}
