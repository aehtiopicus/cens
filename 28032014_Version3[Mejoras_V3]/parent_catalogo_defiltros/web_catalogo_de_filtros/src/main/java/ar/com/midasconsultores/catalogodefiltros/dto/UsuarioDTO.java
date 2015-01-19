/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author bsasschetti
 */
public class UsuarioDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -3262684923699925275L;
	
	@NotEmpty(message = "{constraint.violation.notnull}")
    @NotNull(message = "{constraint.violation.notnull}")
    @Size(min = 1, max = 25, message = "{constraint.violation.size.1_25}")
    private String nombreUsuario;
    @NotEmpty(message = "{constraint.violation.notnull}")
    @NotNull(message = "{constraint.violation.notnull}")
    @Size(min = 1, max = 400, message = "{constraint.violation.size.1_400}")
    @Email(message = "{constraint.violation.email.format}")
    private String email;
    private Boolean enabled;
    private Boolean eliminar = false;
    private Boolean nuevo = false;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEliminar() {
        return eliminar;
    }

    public void setEliminar(Boolean eliminar) {
        this.eliminar = eliminar;
    }

    public Boolean getNuevo() {
        return nuevo;
    }

    public void setNuevo(Boolean nuevo) {
        this.nuevo = nuevo;
    }
}
