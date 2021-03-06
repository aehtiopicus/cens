package com.aehtiopicus.cens.dto.cens;

import java.util.Date;

public class MiembroCensDto {

	private Long id;
	private String nombre;
	private String apellido;	
	private Date fechaNac;
	private String dni;
	private Boolean baja = false;
	private UsuariosDto usuario;	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Date getFechaNac() {
		return fechaNac;
	}
	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public Boolean getBaja() {
		return baja;
	}
	public void setBaja(Boolean baja) {
		this.baja = baja;
	}
	public UsuariosDto getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuariosDto usuario) {
		this.usuario = usuario;
	}
	
	
}
