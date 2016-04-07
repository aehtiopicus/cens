/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

/**
 *
 * @author bsasschetti
 */
public class ListaUsuarioDTO {
    
    @Valid
    private List<UsuarioDTO> usuarioDTOs;

    
    public int getCantUsuarios(){
        return usuarioDTOs.size();
    }
    public List<UsuarioDTO> getUsuarioDTOs() {
        return usuarioDTOs;
    }

    public void setUsuarioDTOs(List<UsuarioDTO> listaUsuarioDTOs) {
        this.usuarioDTOs = listaUsuarioDTOs;
    }
    
    public void limpiarListaNuevosEliminados() {
        List<UsuarioDTO> listaBorrados = new ArrayList<UsuarioDTO>();
        for (UsuarioDTO udto : usuarioDTOs){
            if(udto.getNuevo() && udto.getEliminar()) {
                listaBorrados.add(udto);
            }
        }
        if(!listaBorrados.isEmpty()) {
            this.usuarioDTOs.removeAll(listaBorrados);
        }
    }
    
}
