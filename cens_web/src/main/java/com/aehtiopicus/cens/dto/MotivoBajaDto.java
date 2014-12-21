package com.aehtiopicus.cens.dto;


import org.hibernate.validator.constraints.NotBlank;


public class MotivoBajaDto {

	private Long motivoBajaId;
	
	@NotBlank(message="Dato requerido")
	private String motivo;
	
	private Integer articuloLct;

	public Long getMotivoBajaId() {
		return motivoBajaId;
	}
	public void setMotivoBajaId(Long motivoBajaId) {
		this.motivoBajaId = motivoBajaId;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public Integer getArticuloLct() {
		return articuloLct;
	}
	public void setArticuloLct(Integer articuloLct) {
		this.articuloLct = articuloLct;
	}

}
