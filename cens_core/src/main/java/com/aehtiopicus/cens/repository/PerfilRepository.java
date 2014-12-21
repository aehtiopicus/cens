package com.aehtiopicus.cens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.Perfil;


@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
	public Perfil findByPerfil(String name);	
}
