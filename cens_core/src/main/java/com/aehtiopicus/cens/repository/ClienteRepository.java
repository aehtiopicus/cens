package com.aehtiopicus.cens.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.Usuario;
import com.aehtiopicus.cens.enumeration.EstadoClienteEnum;


@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, Long> ,JpaSpecificationExecutor<Cliente>{

	public Cliente findByNombreIgnoreCase(String nombre);
	
	public Cliente findById(Long id);
	
	public List<Cliente> findByEstadoClienteEnumAndGerenteOperacionOrderByRazonSocialAsc(EstadoClienteEnum estado, Usuario gteOperaciones);

	public List<Cliente> findByFixed(Boolean fixed);

	public List<Cliente> findByEstadoClienteEnumAndJefeOperacionOrderByRazonSocialAsc(EstadoClienteEnum estado, Usuario jefeOperaciones);
	
	public List<Cliente> findByFechaBajaIsNullOrFechaBajaAfterOrFechaBajaOrderByNombreAsc(Date fechaBaja, Date fechaBajaEqual);
}
