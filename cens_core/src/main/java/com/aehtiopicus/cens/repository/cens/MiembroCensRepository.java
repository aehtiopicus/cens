package com.aehtiopicus.cens.repository.cens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.MiembroCens;

@Repository
public interface MiembroCensRepository extends JpaRepository<MiembroCens,Long>{

}
