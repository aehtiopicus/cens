package com.aehtiopicus.cens.repository.cens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.Usuarios;

@Repository
public interface UsuariosCensRepository extends JpaRepository<Usuarios, Long> {

	@Modifying
	@Query("UPDATE Usuarios u SET u.enabled = false WHERE u = (SELECT mc.usuario FROM MiembroCens mc WHERE mc.id = ?1)")
	void softDeleteByMiembro(Long miembroId);

	Usuarios findByUsername(String username);

}
