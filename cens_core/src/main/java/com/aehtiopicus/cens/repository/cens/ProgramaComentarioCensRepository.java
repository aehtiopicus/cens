package com.aehtiopicus.cens.repository.cens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.ProgramaComentario;

@Repository
public interface ProgramaComentarioCensRepository extends JpaRepository<ProgramaComentario, Long>{

}
