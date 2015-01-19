package ar.com.midasconsultores.catalogodefiltros.repository;

import ar.com.midasconsultores.catalogodefiltros.domain.MarcaFiltroPrioridad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MarcaFiltroPrioridadRepository extends JpaRepository<MarcaFiltroPrioridad, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM marca_filtro_prioridad ORDER BY prioridad = 0, prioridad")
    List<MarcaFiltroPrioridad> findAllOrderByPrioridadZeroLast();
}
