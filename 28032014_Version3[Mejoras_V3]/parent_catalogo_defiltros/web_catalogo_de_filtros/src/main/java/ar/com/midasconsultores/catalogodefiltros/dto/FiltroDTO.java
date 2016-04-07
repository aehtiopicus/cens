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
public class FiltroDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3008865710639974118L;

	@NotEmpty
    @NotNull
    private Long id;
	
	private String codigoCorto;
	
	private String codigoLargo;
	
	private String codigoOEM;
	
	private String descripcion;
	
	private String medidas;
	
	private String marca;
	
	private String marcaOEM;
	
	private String tipo;
	
	private String subTipo;
	
	private String largoFiltro;

	private String anchoFiltro;

	private String roscaFiltro;

	private String alturaFiltro;

	private String roscaSensorFiltro; 
	
	private String precioBase;

	public String getSubTipo() {
		return subTipo;
	}

	public void setSubTipo(String subTipo) {
		this.subTipo = subTipo;
	}

	private String foto;

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

	public String getCodigoLargo() {
		return codigoLargo;
	}

	public void setCodigoLargo(String codigoLargo) {
		this.codigoLargo = codigoLargo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMedidas() {
		return medidas;
	}

	public void setMedidas(String medidas) {
		this.medidas = medidas;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	public String getLargoFiltro() {
		return largoFiltro;
	}

	public void setLargoFiltro(String largoFiltro) {
		this.largoFiltro = largoFiltro;
	}

	public String getAnchoFiltro() {
		return anchoFiltro;
	}

	public void setAnchoFiltro(String anchoFiltro) {
		this.anchoFiltro = anchoFiltro;
	}

	public String getRoscaFiltro() {
		return roscaFiltro;
	}

	public void setRoscaFiltro(String roscaFiltro) {
		this.roscaFiltro = roscaFiltro;
	}

	public String getAlturaFiltro() {
		return alturaFiltro;
	}

	public void setAlturaFiltro(String alturaFiltro) {
		this.alturaFiltro = alturaFiltro;
	}

	public String getRoscaSensorFiltro() {
		return roscaSensorFiltro;
	}

	public void setRoscaSensorFiltro(String roscaSensorFiltro) {
		this.roscaSensorFiltro = roscaSensorFiltro;
	}

	public String getCodigoOEM() {
		return codigoOEM;
	}

	public void setCodigoOEM(String codigoOEM) {
		this.codigoOEM = codigoOEM;
	}
	
	public String getPrecioBase() {
		return precioBase;
	}

	public void setPrecioBase(String precioBase) {
		this.precioBase = precioBase;
	}

	public String getMarcaOEM() {
		return marcaOEM;
	}

	public void setMarcaOEM(String marcaOEM) {
		this.marcaOEM = marcaOEM;
	}
	
}
