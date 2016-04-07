/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author cgaia
 */
public class FiltroReemplazoDTO implements Serializable {

	private static final long serialVersionUID = -3008865710639974118L;

	@NotEmpty
    @NotNull
    private Long id;
	
	private String codigoCorto;
	
	private String marca;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoCorto() {
		return codigoCorto;
	}

	public void setCodigoCorto(String codigoCorto) {
		this.codigoCorto = codigoCorto;
	}
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}
	
}
