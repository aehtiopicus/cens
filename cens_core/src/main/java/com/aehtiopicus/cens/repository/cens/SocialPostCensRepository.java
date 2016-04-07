package com.aehtiopicus.cens.repository.cens;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.domain.entities.SocialPost;

@Repository
public interface SocialPostCensRepository extends JpaRepository<SocialPost, Long>{

	public SocialPost findByPrograma(Programa p);

	@Query(nativeQuery=true,value="DELETE FROM cens_social_postted_data WHERE programa_id = :pId")
	@Modifying
	public int deleteByPrograma(@Param("pId") Long pid);

}
