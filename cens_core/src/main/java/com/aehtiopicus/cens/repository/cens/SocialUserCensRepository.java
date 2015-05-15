package com.aehtiopicus.cens.repository.cens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.entities.SocialUserConnection;

@Repository
public interface SocialUserCensRepository extends JpaRepository<SocialUserConnection, Long>{

	public SocialUserConnection findByProviderId(String string);

}
