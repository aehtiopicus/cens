package com.aehtiopicus.cens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> , JpaSpecificationExecutor<Usuario>{
	  


	@Query("select count(*) from Usuario u")
	public Long getCantidadUsuarios();

	public Usuario findByUsernameIgnoreCase(String username);

}
