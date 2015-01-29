package com.aehtiopicus.cens.repository.cens;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.ComentarioCens;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;



@Repository
public interface ComentarioCensRepository extends JpaRepository<ComentarioCens, Long>{

	List<ComentarioCens> findByTipoIdAndTipoComentarioAndBajaFalseAndParentIsNull(Long tipoId,
			ComentarioType tipoType, Sort sortByFechaAsc);

}
