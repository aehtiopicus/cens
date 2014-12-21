package com.aehtiopicus.cens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.Sueldo;


@Repository
public interface SueldoRepository extends JpaRepository<Sueldo, Long> ,JpaSpecificationExecutor<Sueldo> {
	
	public Sueldo findById(Long id);

	
		
}
