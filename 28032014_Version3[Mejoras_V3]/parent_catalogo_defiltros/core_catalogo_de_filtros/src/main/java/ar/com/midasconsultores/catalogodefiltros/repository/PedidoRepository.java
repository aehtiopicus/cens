package ar.com.midasconsultores.catalogodefiltros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import ar.com.midasconsultores.catalogodefiltros.domain.Pedido;


public interface PedidoRepository extends JpaRepository<Pedido, Long>{

	public Pedido findPedidoByBaja(@Param("baja") boolean baja);
}
