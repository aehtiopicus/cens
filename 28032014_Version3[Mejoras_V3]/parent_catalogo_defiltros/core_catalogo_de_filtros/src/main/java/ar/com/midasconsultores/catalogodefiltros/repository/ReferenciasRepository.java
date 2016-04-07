package ar.com.midasconsultores.catalogodefiltros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.midasconsultores.catalogodefiltros.domain.Referencias;

public interface ReferenciasRepository extends JpaRepository<Referencias, Long>{

	public Referencias findByValor(String valor);
}
