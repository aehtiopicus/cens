package ar.com.midasconsultores.catalogodefiltros.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.com.midasconsultores.catalogodefiltros.domain.Filtro;
import ar.com.midasconsultores.catalogodefiltros.domain.Vehiculo;
import org.springframework.data.jpa.repository.Modifying;

@Transactional(propagation = Propagation.REQUIRED)
public interface FiltroRepository extends QueryDslPredicateExecutor<Filtro>, JpaRepository<Filtro, Long> {
    
	public Page<Filtro> findAllByReemplazosAndPropio(Filtro reemplazo, Boolean propio, Pageable pageable);
	
	public Page<Filtro> findAllByAplicaciones(Vehiculo aplicacion, Pageable pageable);
	
	@Query(value = "SELECT f FROM Filtro f WHERE lower(f.codigoCortoLimpio) like CONCAT('%', CONCAT(lower(:codigoCortoLimpio), '%')) ORDER BY f.codigoCortoLimpio ASC")
	public List<Filtro> findAllByCodigoCortoLimpio(@Param(value="codigoCortoLimpio") String codigoCortoLimpio, Pageable pageable);
	
	@Query(value = "SELECT Object(f) FROM Filtro f, IN (f.aplicaciones) AS a WHERE a = :aplicacion")
	public Page<Filtro> listFiltrosParaAplicacion(
			@Param(value = "aplicacion") Vehiculo aplicacion, Pageable pageable);

	@Query(nativeQuery = true, value = "Select distinct(f.codigolargo), f.marca  from filtroparaqueriesoptimizadas AS fo JOIN filtroparaqueriesoptimizadas_filtro AS fo_f ON fo.id =fo_f.filtro_id JOIN filtro as f ON f.id= fo_f.reemplazos_id\r\n"
			+ "WHERE  fo.id_filtro = ?1 AND fo.propio = true AND (f.codigocortolimpio ILIKE ?2 OR f.codigolargolimpio ILIKE ?2) AND NOT f.codigolargo = '' limit 2")
	public List<Object[]> listarCodigosOEMParaBusquedaParcial(Long idFiltro,
			String busqueda);

        @Modifying
        @Query(nativeQuery = true, value="UPDATE filtro SET prioridadmarca = :prioridad WHERE marca = :marca_encriptada")
        public void updatePrioridadByMarca(@Param("marca_encriptada") String marca_encriptada, @Param("prioridad") Integer prioridad);

}