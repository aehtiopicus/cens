package com.aehtiopicus.cens.repository.cens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.FileCensInfo;

@Repository
public interface FileCensRepository extends JpaRepository<FileCensInfo,Long>{

	@Modifying
	@Query("UPDATE FileCensInfo fci SET baja = true WHERE fci = :fci")
	public int softDelete(@Param("fci")FileCensInfo fileInfo);
	
	@Modifying
	@Query("UPDATE FileCensInfo fci SET fci.fileLocationPath =:pathNew WHERE fci.fileLocationPath = :pathOld")
	public int updateFileInfo(@Param("pathOld")String pathToChange,@Param("pathNew") String newPath);

}
