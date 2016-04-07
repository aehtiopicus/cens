package ar.com.midasconsultores.catalogodefiltros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.com.midasconsultores.catalogodefiltros.domain.FiltroParaQueriesOptimizadas;
import org.springframework.data.jpa.repository.Modifying;

@Transactional(propagation = Propagation.REQUIRED)
public interface FiltroParaQueriesOptimizadasRepository extends QueryDslPredicateExecutor<FiltroParaQueriesOptimizadas>, JpaRepository<FiltroParaQueriesOptimizadas, Long> {

	@Query(nativeQuery = true, value="select * from filtroparaqueriesoptimizadas f where f.codigocorto = :codigocorto LIMIT 1")
	FiltroParaQueriesOptimizadas findOneByCodigocorto(@Param("codigocorto") String codigocorto);
        
        @Modifying
        @Query(nativeQuery = true, value="UPDATE filtroparaqueriesoptimizadas SET prioridadmarca = :prioridad WHERE marca = :marca_encriptada")
	void updatePrioridadByMarca(@Param("marca_encriptada") String marca_encriptada, @Param("prioridad") Integer prioridad);
		  
}