package com.aehtiopicus.cens.repository.cens;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.Usuarios;

@Repository
public interface UsuariosCensRepository extends JpaRepository<Usuarios, Long> {

	@Modifying
	@Query("UPDATE Usuarios u SET u.enabled = false WHERE u = (SELECT mc.usuario FROM MiembroCens mc WHERE mc.id = ?1)")
	public int softDeleteByMiembro(Long miembroId);

	public Usuarios findByUsername(String username);

	@Modifying
	@Query("UPDATE Usuarios u SET u.password = :password WHERE u.id = :id")
	public int resetPassword(@Param("id")Long usuarioId,@Param("password") String defaulPassword);

	@Query(value="SELECT u.username, c.datocontacto, mc.apellido, mc.nombre, mc.dni FROM cens_usuarios u "
			+ "INNER JOIN cens_miembros_cens mc ON u.id = mc.usuario_id "
			+ "INNER JOIN cens_contacto c ON c.miembrocens_id = mc.id  "
			+ "WHERE u.enabled = true "
			+ "AND c.tipocontacto = 'MAIL' ",nativeQuery=true)
	public List<Object[]> findUsernameActivos();
	
	@Query(value="SELECT u.username, c.datocontacto, mc.apellido, mc.nombre, mc.dni FROM cens_usuarios u "
			+ "INNER JOIN cens_miembros_cens mc ON u.id = mc.usuario_id "
			+ "WHERE u.enabled = true ",nativeQuery=true)
	public List<Object[]> findUsernameActivosWithoutEnamil();
	
	
	@Query(value="SELECT u.username, c.datocontacto, mc.apellido, mc.nombre, mc.dni FROM cens_usuarios u "
			+ "INNER JOIN cens_miembros_cens mc ON u.id = mc.usuario_id "
			+ "INNER JOIN cens_contacto c ON c.miembrocens_id = mc.id  "
			+ "INNER JOIN cens_perfil_usuario_cens cpuc ON u.id = cpuc.usuario_id "
			+ "WHERE u.enabled = true "
			+ "AND cpuc.perfiltype ='ROLE_ASESOR' "
			+ "AND c.tipocontacto = 'MAIL' ",nativeQuery=true)
	public List<Object[]> findAsesorActivos();
	
	@Query(value="select ids.asesor_ids from (select 'ids' as id,string_agg(CAST(miembrocens_id as TEXT),',' ) as asesor_ids from cens_asesor where id <> -1 group by 1) ids", nativeQuery=true)
	public String findAllAsesorIds();

}
