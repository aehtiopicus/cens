package com.aehtiopicus.cens.repository.cens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.CambioEstadoCensFeed;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;

@Repository
public interface CambioEstadoCensRepository extends JpaRepository<CambioEstadoCensFeed, Long>{


	@Modifying
	@Query(value="Update  CambioEstadoCensFeed c SET c.activityFeed.readed = true WHERE c.tipoId = ?1 AND c.activityFeed.toId = ?2 AND c.activityFeed.comentarioType = ?3 WHERE c.activityFeed.readed = false")
	public int markCambioEstadoFeedAsRead(Long ctId, Long miembroId, ComentarioType ct);

}
