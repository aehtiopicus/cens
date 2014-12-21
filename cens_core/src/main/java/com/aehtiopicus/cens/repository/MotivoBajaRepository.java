package com.aehtiopicus.cens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.MotivoBaja;


@Repository
public interface MotivoBajaRepository extends JpaRepository<MotivoBaja, Long> ,JpaSpecificationExecutor<MotivoBaja> {

}
