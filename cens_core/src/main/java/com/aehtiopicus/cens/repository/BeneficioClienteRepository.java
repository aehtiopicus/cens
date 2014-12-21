package com.aehtiopicus.cens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.Beneficio;
import com.aehtiopicus.cens.domain.BeneficioCliente;
import com.aehtiopicus.cens.domain.Cliente;

@Repository
public interface BeneficioClienteRepository extends JpaRepository<BeneficioCliente, Long>{


}
