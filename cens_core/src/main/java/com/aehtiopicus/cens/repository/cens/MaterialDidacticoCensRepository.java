package com.aehtiopicus.cens.repository.cens;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.MaterialDidactico;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;

@Repository
public interface MaterialDidacticoCensRepository extends JpaRepository<MaterialDidactico, Long>, JpaSpecificationExecutor<MaterialDidactico>{
	

	public MaterialDidactico findByProgramaAndNombre(Programa programa,
			String nombre);

	@Modifying
	@Query("UPDATE MaterialDidactico md SET md.fileInfo = null, md.estadoRevisionType = :nuevo, fechaCambioEstado = :fecha WHERE md = :material")
	public int removeFileInfo(@Param("material")MaterialDidactico p, @Param("nuevo")EstadoRevisionType nuevo, @Param("fecha") Date fecha);

	@Modifying
	@Query("UPDATE MaterialDidactico md SET md.estadoRevisionType = :nuevo, fechaCambioEstado = :fecha WHERE md.id = :materialId")
	public void updateMaterialDidacticoStatus(@Param("materialId")Long materialId,@Param("nuevo")EstadoRevisionType estadoRevisionType, @Param("fecha") Date fecha);

}
