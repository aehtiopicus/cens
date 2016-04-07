/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.service;

import ar.com.midasconsultores.catalogodefiltros.domain.MarcaFiltroPrioridad;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 *
 * @author bsasschetti
 */
public interface MarcaFiltroPrioridadService {

    MarcaFiltroPrioridad get(Long idMarcaFiltroPrioridad);

    Page<MarcaFiltroPrioridad> list(int page, int start, int limit);

    List<MarcaFiltroPrioridad> list();

    void save(MarcaFiltroPrioridad marcaFiltroPrioridad);

    void saveAll(List<MarcaFiltroPrioridad> listaMarcaFiltroPrioridad);
    
}
