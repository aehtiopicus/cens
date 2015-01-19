package ar.com.midasconsultores.catalogodefiltros.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.com.midasconsultores.catalogodefiltros.domain.ClienteLista;

public interface ClienteListaRepository extends JpaRepository<ClienteLista, Long> {

	public ClienteLista findByCodigoClienteIgnoreCase(String codigoCliente);

	@Query(value = "SELECT f FROM ClienteLista f WHERE lower(f.codigoCliente) like CONCAT('%', CONCAT(lower(:codigoCliente), '%')) ORDER BY f.codigoCliente ASC")
	public List<ClienteLista> findAllByCodigoCliente(@Param(value="codigoCliente") String codigoCliente, Pageable pageable);
            
}