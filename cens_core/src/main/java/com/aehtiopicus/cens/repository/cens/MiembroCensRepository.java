package com.aehtiopicus.cens.repository.cens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.UsuarioPerfil;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;

@Repository
public interface MiembroCensRepository extends JpaRepository<MiembroCens,Long>,JpaSpecificationExecutor<MiembroCens>{

	@Query("FROM MiembroCens mc WHERE mc.usuario = :usuario AND mc.baja = false")
	public MiembroCens findOneByUsuario(@Param("usuario")Usuarios usuario);

}
