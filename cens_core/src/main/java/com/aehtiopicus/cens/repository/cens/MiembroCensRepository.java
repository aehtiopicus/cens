package com.aehtiopicus.cens.repository.cens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Usuarios;

@Repository
public interface MiembroCensRepository extends JpaRepository<MiembroCens,Long>,JpaSpecificationExecutor<MiembroCens>{

	@Query("FROM MiembroCens mc WHERE mc.usuario = :usuario AND mc.baja = false")
	public MiembroCens findOneByUsuario(@Param("usuario")Usuarios usuario);

	@Modifying
	@Query("UPDATE MiembroCens mc  SET mc.baja = true WHERE mc.id = :miembroId")
	public int softDelete(@Param("miembroId")Long miembroId);

	public MiembroCens findByDni(String dni);

	@Query("SELECT mc FROM MiembroCens mc WHERE mc.usuario.username =?1")
	public MiembroCens findByUsername(String username);

}
