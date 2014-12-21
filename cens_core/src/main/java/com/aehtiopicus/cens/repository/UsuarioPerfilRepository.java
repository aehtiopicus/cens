package com.aehtiopicus.cens.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.Perfil;
import com.aehtiopicus.cens.domain.Usuario;
import com.aehtiopicus.cens.domain.UsuarioPerfil;


@Repository
public interface UsuarioPerfilRepository extends JpaRepository<UsuarioPerfil, Long> ,JpaSpecificationExecutor<UsuarioPerfil> {
	
	public UsuarioPerfil findByUsuario(Usuario usuario);
	
	public List<UsuarioPerfil> findByPerfil(Perfil perfil);
	 
	  @Query("select count(*) from UsuarioPerfil up where up.id = :id ")
	  public Long getCantidadUsuarios(@Param("id") long id);
	
	 
	
}
