/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.admin.dto;

import java.util.List;
import javax.validation.Valid;

/**
 *
 * @author bsasschetti
 */
public class ListaMarcaFiltroPrioridadDTO {
    
    @Valid
    List<MarcaFiltroPrioridadDTO> marcaFiltroPrioridadDTOs;

    public List<MarcaFiltroPrioridadDTO> getMarcaFiltroPrioridadDTOs() {
        return marcaFiltroPrioridadDTOs;
    }

    public void setMarcaFiltroPrioridadDTOs(List<MarcaFiltroPrioridadDTO> marcaFiltroPrioridadDTOs) {
        this.marcaFiltroPrioridadDTOs = marcaFiltroPrioridadDTOs;
    }
    
    
}
