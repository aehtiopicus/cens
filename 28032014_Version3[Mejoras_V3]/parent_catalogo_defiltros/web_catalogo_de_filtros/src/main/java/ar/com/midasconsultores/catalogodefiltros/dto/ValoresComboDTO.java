/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.dto;

import java.io.Serializable;

/**
 *
 * @author cgaia
 */
public class ValoresComboDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4644887509108611011L;

	private String label;
	
	private String valor;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
