package com.aehtiopicus.cens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.Vacaciones;


@Repository
public interface VacacionesRepository extends JpaRepository<Vacaciones, Long> ,JpaSpecificationExecutor<Vacaciones> {
	
	public Vacaciones findById(Long id);

	
		
}
