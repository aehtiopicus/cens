package com.aehtiopicus.cens.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class UsuarioDto {

	protected Long usuarioId;

	@NotBlank(message="Debe cargar un nombre de usuario.")
	protected String username;
	
	@NotNull(message="Debe seleccionar un perfil.")
	protected Long perfilId;

	protected String password;
	
	protected String passwordConfirmation;
	
	@NotBlank(message="Nombre no puede estar vacío")
	protected String nombre;
	@NotBlank(message="Apellido no puede estar vacío")
	protected String apellido;
	
	@Pattern(regexp="|^[a-zA-Z0-9._-]{2,100}@[a-zA-Z0-9._-]{2,100}.[a-zA-Z]{2,4}(.[a-zA-Z]{2,4})?$", message="Formato de e-mail incorrecto.")
	protected String email;
	
	protected String profileName;
	
	public Long getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getPerfilId() {
		return perfilId;
	}
	public void setPerfilId(Long perfilId) {
		this.perfilId = perfilId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
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
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	
	
}
