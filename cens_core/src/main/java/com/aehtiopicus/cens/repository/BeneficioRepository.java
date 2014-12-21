package com.aehtiopicus.cens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.Beneficio;

@Repository
public interface BeneficioRepository extends JpaRepository<Beneficio, Long>{
	public Beneficio findById(Long id);

	public Beneficio findByTituloIgnoreCase(String titulo);

}
