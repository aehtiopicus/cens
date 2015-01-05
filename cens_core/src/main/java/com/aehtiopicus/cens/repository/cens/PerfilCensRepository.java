package com.aehtiopicus.cens.repository.cens;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aehtiopicus.cens.domain.entities.Perfil;
import com.aehtiopicus.cens.domain.entities.Usuarios;

public interface PerfilCensRepository extends JpaRepository<Perfil,Long>{

	public List<Perfil> findByUsuario(Usuarios usuarios);
}
