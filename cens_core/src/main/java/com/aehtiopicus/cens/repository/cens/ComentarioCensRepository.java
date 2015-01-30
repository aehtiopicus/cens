package com.aehtiopicus.cens.repository.cens;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.ComentarioCens;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;



@Repository
public interface ComentarioCensRepository extends JpaRepository<ComentarioCens, Long>{

	public List<ComentarioCens> findByTipoIdAndTipoComentarioAndBajaFalseAndParentIsNull(Long tipoId,
			ComentarioType tipoType, Sort sortByFechaAsc);

	@Modifying
	@Query("UPDATE ComentarioCens cc SET baja = true WHERE cc.id = :comentarioId")
	public int softDelete(@Param("comentarioId")Long comentarioId);

	@Modifying
	@Query("UPDATE ComentarioCens cc SET fileCensInfo = null WHERE cc = :comentario")
	public int removeFileInfo(@Param("comentario")ComentarioCens cc);

}
