package com.aehtiopicus.cens.dto;

import org.hibernate.validator.constraints.NotBlank;


public class ChangePasswordDto {
	protected String username;
	
	@NotBlank(message="Password acual no puede estar vacio.")
	protected String passwordActual;
	
	@NotBlank(message="Nuevo password no puede estar vacio.")
	protected String passwordNuevo;
	
	@NotBlank(message="Confirmacion de password no puede estar vacio.")
	protected String passwordNuevoConfirmation;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswordActual() {
		return passwordActual;
	}
	public void setPasswordActual(String passwordActual) {
		this.passwordActual = passwordActual;
	}
	public String getPasswordNuevo() {
		return passwordNuevo;
	}
	public void setPasswordNuevo(String passwordNuevo) {
		this.passwordNuevo = passwordNuevo;
	}
	public String getPasswordNuevoConfirmation() {
		return passwordNuevoConfirmation;
	}
	public void setPasswordNuevoConfirmation(String passwordNuevoConfirmation) {
		this.passwordNuevoConfirmation = passwordNuevoConfirmation;
	}
	

	
}
