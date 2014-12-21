package com.aehtiopicus.cens.dto;

import javax.validation.constraints.Pattern;

public class MiPerfilDto {
	protected String username;
	
	protected String nombre;
	protected String apellido;
	
	@Pattern(regexp="|^[a-zA-Z0-9._-]{2,100}@[a-zA-Z0-9._-]{2,100}.[a-zA-Z]{2,4}(.[a-zA-Z]{2,4})?$", message="Formato de e-mail incorrecto.")
	protected String email;


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
