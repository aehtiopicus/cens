package com.aehtiopicus.cens.dto.cens;

import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;

public class MaterialDidacticoDto {

	private Long id;	
	private ProfesorDto profesor;
	private String nombre;
	private ProgramaDto programa;	
	private String descripcion;
	private int nro;		
	private EstadoRevisionType estadoRevisionType = EstadoRevisionType.NUEVO;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ProfesorDto getProfesor() {
		return profesor;
	}
	public void setProfesor(ProfesorDto profesor) {
		this.profesor = profesor;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ProgramaDto getPrograma() {
		return programa;
	}
	public void setPrograma(ProgramaDto programa) {
		this.programa = programa;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getNro() {
		return nro;
	}
	public void setNro(int nro) {
		this.nro = nro;
	}
	public EstadoRevisionType getEstadoRevisionType() {
		return estadoRevisionType;
	}
	public void setEstadoRevisionType(EstadoRevisionType estadoRevisionType) {
		this.estadoRevisionType = estadoRevisionType;
	}
	
	
	
	
}
