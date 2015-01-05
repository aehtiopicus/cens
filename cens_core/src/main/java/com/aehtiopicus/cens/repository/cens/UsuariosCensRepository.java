package com.aehtiopicus.cens.repository.cens;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aehtiopicus.cens.domain.entities.Usuarios;

public interface UsuariosCensRepository extends JpaRepository<Usuarios, Long> {

}
